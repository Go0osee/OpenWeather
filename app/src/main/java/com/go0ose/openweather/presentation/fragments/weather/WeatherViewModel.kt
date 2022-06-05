package com.go0ose.openweather.presentation.fragments.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.go0ose.openweather.domain.CityBaseInteractor
import com.go0ose.openweather.domain.WeatherInteractor
import com.go0ose.openweather.utils.ext.toCityCoordinates
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.domain.model.WeatherWrapper
import com.go0ose.openweather.presentation.mainActivity.MainViewModel
import com.go0ose.openweather.utils.prefs.SharedPreferenceImpl
import com.go0ose.openweather.utils.prefs.SharedPreferenceManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val weatherInteractor: WeatherInteractor,
    private val cityBaseInteractor: CityBaseInteractor,
    private val prefs: SharedPreferenceManager
) : ViewModel() {

    companion object {
        private const val BACKGROUND_KEY = "BACKGROUND_KEY"
    }

    private val _weatherWrapper = MutableLiveData<WeatherWrapper>()
    val weatherWrapper: LiveData<WeatherWrapper> get() = _weatherWrapper

    fun loadWeather(cityWeatherFromDataBase: CityWeatherFromDataBase) {
        viewModelScope.launch {
            _weatherWrapper.postValue(weatherInteractor.getWeather(cityWeatherFromDataBase.toCityCoordinates()))
        }
    }

    fun addToDataBase(cityWeatherFromDataBase: CityWeatherFromDataBase) {
        viewModelScope.launch {
            cityBaseInteractor.addCityWeatherFromDataBase(cityWeatherFromDataBase)
        }
    }

    fun changeFavorite(cityWeatherFromDataBase: CityWeatherFromDataBase) {
        viewModelScope.launch {
            val list = cityBaseInteractor.getAllCitiesCoordinatesFromBase()
            list.forEach { city ->
                if (city.favorite == 1) {
                    city.favorite = 2
                }
                if(city.fullCityName == cityWeatherFromDataBase.fullCityName) {
                    city.favorite = 1
                }
            }
            cityBaseInteractor.updateAllCityWeather(list)
        }
    }

    fun loadBackgroundToPref(backgroundId: Int) {
        prefs.saveString(BACKGROUND_KEY, backgroundId.toString())
    }
}