package com.go0ose.openweather.presentation.fragments.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.go0ose.openweather.R
import com.go0ose.openweather.domain.CityBaseInteractor
import com.go0ose.openweather.domain.WeatherInteractor
import com.go0ose.openweather.utils.mapper.toCityCoordinates
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.domain.model.WeatherWrapper
import com.go0ose.openweather.utils.AppConstants.BACKGROUND_KEY
import com.go0ose.openweather.utils.prefs.SharedPreferenceManager
import com.go0ose.openweather.utils.resource.ResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val weatherInteractor: WeatherInteractor,
    private val cityBaseInteractor: CityBaseInteractor,
    private val prefs: SharedPreferenceManager,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _weatherWrapper = MutableLiveData<WeatherWrapper>()
    val weatherWrapper: LiveData<WeatherWrapper> get() = _weatherWrapper

    fun loadWeather(cityWeatherFromDataBase: CityWeatherFromDataBase) {
        viewModelScope.launch {
            var weather = weatherInteractor.getWeather(cityWeatherFromDataBase.toCityCoordinates())

            weather.dailyItems[0].day = resourceProvider.getString(R.string.today)
            weather.dailyItems[1].day = resourceProvider.getString(R.string.tomorrow)
            weather.hourlyItems[0].time = resourceProvider.getString(R.string.now)

            _weatherWrapper.postValue(weather)
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
                if (city.fullCityName == cityWeatherFromDataBase.fullCityName) {
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