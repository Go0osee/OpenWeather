package com.go0ose.openweather.presentation.mainActivity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.go0ose.openweather.domain.CityBaseInteractor
import com.go0ose.openweather.domain.WeatherInteractor
import com.go0ose.openweather.utils.mapper.toCityCoordinates
import com.go0ose.openweather.utils.mapper.toCityWeatherFromDataBaseFirstLogin
import com.go0ose.openweather.domain.model.CityCoordinates
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.utils.prefs.SharedPreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val cityBaseInteractor: CityBaseInteractor,
    private val weatherInteractor: WeatherInteractor,
    private val prefs: SharedPreferenceManager
) : ViewModel() {

    companion object {
        private const val FIRST_LOGIN_KEY = "FIRST_LOGIN_KEY"
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val _cityWeatherFromDataBase = MutableLiveData<CityWeatherFromDataBase>()
    val cityWeatherFromDataBase: LiveData<CityWeatherFromDataBase> get() = _cityWeatherFromDataBase

    private val _mainCities = MutableLiveData<CityWeatherFromDataBase>()
    val mainCities: LiveData<CityWeatherFromDataBase> get() = _mainCities

    private fun loadCityWeatherInternet(cityCoordinates: CityCoordinates) {
        viewModelScope.launch {
            _cityWeatherFromDataBase.postValue(
                weatherInteractor.getWeather(cityCoordinates).toCityWeatherFromDataBaseFirstLogin()
            )
        }
    }

    fun getLastLocation(context: Context) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    loadCityWeatherInternet(
                        CityCoordinates(
                            lat = location.latitude.toString(),
                            lon = location.longitude.toString()
                        )
                    )
                }
            }
        }
    }

    fun updateMainCityInBase() {
        viewModelScope.launch {
            cityBaseInteractor.getAllCitiesCoordinatesFromBase()
                .forEach { cityWeatherFromDataBase ->
                    if (cityWeatherFromDataBase.favorite == 1) {
                        val cityWeatherFromBase =
                            weatherInteractor.getWeather(cityWeatherFromDataBase.toCityCoordinates())
                                .toCityWeatherFromDataBaseFirstLogin()

                        cityWeatherFromBase.id = cityWeatherFromDataBase.id
                        cityBaseInteractor.updateCityWeather(cityWeatherFromBase)
                    }
                }
        }
    }

    fun isFirstLogin() = prefs.getString(FIRST_LOGIN_KEY).isEmpty()
    private fun saveFirstLogin() = prefs.saveString(FIRST_LOGIN_KEY, "true")

    fun saveCityToBase(cityWeatherFromDataBase: CityWeatherFromDataBase) {
        viewModelScope.launch {
            cityBaseInteractor.addCityWeatherFromDataBase(cityWeatherFromDataBase)
        }.invokeOnCompletion {
            saveFirstLogin()
        }
    }

    fun getMainCity() {
        viewModelScope.launch {
            cityBaseInteractor.getAllCitiesCoordinatesFromBase()
                .forEach { cityWeatherFromDataBase ->
                    if (cityWeatherFromDataBase.favorite == 1) {
                        _mainCities.postValue(cityWeatherFromDataBase)
                    }
                }
        }
    }
}