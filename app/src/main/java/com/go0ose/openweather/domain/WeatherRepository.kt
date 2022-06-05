package com.go0ose.openweather.domain

import com.go0ose.openweather.data.retrofit.CityResponse
import com.go0ose.openweather.data.retrofit.WeatherResponse
import com.go0ose.openweather.domain.model.CityCoordinates

interface WeatherRepository {
    suspend fun getWeather(cityCoordinates: CityCoordinates): WeatherResponse
    suspend fun getCoordinatesByCity(q: String): CityResponse
}