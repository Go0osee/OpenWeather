package com.go0ose.openweather.domain

import com.go0ose.openweather.domain.model.CityCoordinates
import com.go0ose.openweather.domain.model.CityItem
import com.go0ose.openweather.domain.model.WeatherWrapper

interface WeatherInteractor {

    suspend fun getWeather(cityCoordinates: CityCoordinates): WeatherWrapper

    suspend fun getCoordinatesByCity(q: String): List<CityItem>
}