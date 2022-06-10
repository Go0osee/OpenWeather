package com.go0ose.openweather.presentation.fragments.citybase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.go0ose.openweather.domain.CityBaseInteractor
import com.go0ose.openweather.domain.WeatherInteractor
import com.go0ose.openweather.utils.mapper.toCityCoordinates
import com.go0ose.openweather.utils.mapper.toCityWeatherFromDataBase
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.domain.model.WeatherWrapper
import com.go0ose.openweather.utils.AppConstants.BACKGROUND_KEY
import com.go0ose.openweather.utils.mapper.toFirstFavorite
import com.go0ose.openweather.utils.prefs.SharedPreferenceManager
import kotlinx.coroutines.launch

class CityBaseViewModel(
    private val cityBaseInteractor: CityBaseInteractor,
    private val weatherInteractor: WeatherInteractor,
    private val prefs: SharedPreferenceManager
) : ViewModel() {

    private val _weatherWrapperList = MutableLiveData<List<WeatherWrapper>>()
    val weatherWrapperList: LiveData<List<WeatherWrapper>> get() = _weatherWrapperList

    private val _cityWeatherFromDataBase = MutableLiveData<List<CityWeatherFromDataBase>>()
    val cityWeatherFromDataBase: LiveData<List<CityWeatherFromDataBase>> get() = _cityWeatherFromDataBase

    fun loadCityWeatherFromDataBase() {
        viewModelScope.launch {
            _cityWeatherFromDataBase.postValue(
                cityBaseInteractor.getAllCitiesCoordinatesFromBase().toFirstFavorite()
            )
        }
    }

    fun deleteCityFromLiveData(position: Int) {
        var list = _cityWeatherFromDataBase.value as MutableList<CityWeatherFromDataBase>
        list.removeAt(position)

        _cityWeatherFromDataBase.postValue(list)
    }

    fun loadCityWeatherInternet() {

        viewModelScope.launch {
            val cityWeatherListNew =
                cityBaseInteractor.getAllCitiesCoordinatesFromBase().toFirstFavorite()
                    .map { cityWeatherFromDataBase ->
                        val newWeather = weatherInteractor.getWeather(
                            cityWeatherFromDataBase.toCityCoordinates()
                        ).toCityWeatherFromDataBase()
                        newWeather.id = cityWeatherFromDataBase.id
                        newWeather.favorite = cityWeatherFromDataBase.favorite
                        return@map newWeather
                    }
            _cityWeatherFromDataBase.postValue(cityWeatherListNew)
            cityBaseInteractor.updateAllCityWeather(cityWeatherListNew)
        }
    }

    fun deleteCity(cityWeatherFromDataBase: CityWeatherFromDataBase) {
        viewModelScope.launch {
            cityBaseInteractor.deleteCityCoordinatesFromBase(cityWeatherFromDataBase)
        }
    }

    fun loadBackgroundFromPrefs(): Int = prefs.getString(BACKGROUND_KEY).toInt()
}