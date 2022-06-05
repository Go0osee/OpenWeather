package com.go0ose.openweather.data

import com.go0ose.openweather.data.retrofit.*
import com.go0ose.openweather.domain.WeatherRepository
import com.go0ose.openweather.domain.model.CityCoordinates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val cityByCoordinatesApi: CityByCoordinatesApi,
    private val coordinatesByCityApi: CoordinatesByCityApi
) : WeatherRepository {
    override suspend fun getWeather(CityCoordinates: CityCoordinates): WeatherResponse {
        return withContext(Dispatchers.IO) {

            val weatherResponse = weatherApi.getWeatherResponse(
                lat = CityCoordinates.lat,
                lon = CityCoordinates.lon
            )

            val city = cityByCoordinatesApi.getCityResponse(
                lat = CityCoordinates.lat,
                lon = CityCoordinates.lon
            ).features[0]

            weatherResponse.cityName = city.text
            weatherResponse.fullCityName = city.placeName

            return@withContext weatherResponse
        }
    }

    override suspend fun getCoordinatesByCity(q: String): CityResponse {
        return withContext(Dispatchers.IO) {
            coordinatesByCityApi.getCoordinatesResponse(q)
        }
    }
}