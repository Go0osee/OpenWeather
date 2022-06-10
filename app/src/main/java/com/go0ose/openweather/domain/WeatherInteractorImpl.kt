package com.go0ose.openweather.domain

import com.go0ose.openweather.utils.mapper.toCityItem
import com.go0ose.openweather.utils.mapper.toWeatherWrapper
import com.go0ose.openweather.domain.model.CityCoordinates
import com.go0ose.openweather.domain.model.CityItem
import com.go0ose.openweather.domain.model.WeatherWrapper

class WeatherInteractorImpl(
    private val repository: WeatherRepository
    ) : WeatherInteractor {

    override suspend fun getWeather(cityCoordinates: CityCoordinates): WeatherWrapper {
        return repository.getWeather(cityCoordinates).toWeatherWrapper()
    }

    override suspend fun getCoordinatesByCity(q: String): List<CityItem> {
        return repository.getCoordinatesByCity(q).features.map { features ->
            features.toCityItem()
        }
    }
}