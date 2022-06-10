package com.go0ose.openweather.presentation.widget

import androidx.lifecycle.ViewModel
import com.go0ose.openweather.domain.CityBaseInteractor
import com.go0ose.openweather.domain.WeatherInteractor
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.domain.model.WeatherWrapper
import com.go0ose.openweather.utils.mapper.toCityCoordinates

class WidgetViewModel(
    private val weatherInteractor: WeatherInteractor,
    private val cityBaseInteractor: CityBaseInteractor
) : ViewModel() {

    suspend fun loadWeather(): WeatherWrapper {
        var cityWeatherFromBase: CityWeatherFromDataBase? = null
        cityBaseInteractor.getAllCitiesCoordinatesFromBase()
            .forEach { cityWeatherFromDataBase ->
                if (cityWeatherFromDataBase.favorite == 1) {
                    cityWeatherFromBase = cityWeatherFromDataBase
                }
            }

        return weatherInteractor.getWeather(cityWeatherFromBase!!.toCityCoordinates())
    }
}