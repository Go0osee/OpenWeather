package com.go0ose.openweather.data

import com.go0ose.openweather.data.retrofit.*
import com.go0ose.openweather.domain.WeatherRepository
import com.go0ose.openweather.domain.model.CityCoordinates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val cityByCoordinatesApi: CityByCoordinatesApi,
    private val coordinatesByCityApi: CoordinatesByCityApi
) : WeatherRepository {

    override suspend fun getWeather(cityCoordinates: CityCoordinates): WeatherResponse {
        return withContext(Dispatchers.IO) {

            val weatherResponse = weatherApi.getWeatherResponse(
                lat = cityCoordinates.lat,
                lon = cityCoordinates.lon,
                lang = getLang()
            )

            try {
                val city = cityByCoordinatesApi.getCityResponse(
                    lat = cityCoordinates.lat,
                    lon = cityCoordinates.lon,
                    language = getLang()
                ).features[0]

                weatherResponse.cityName = city.text
                weatherResponse.fullCityName = city.placeName
            } catch (e: Exception) {
                weatherResponse.cityName = "---"
                weatherResponse.fullCityName = "---"
            }

            return@withContext weatherResponse
        }
    }

    override suspend fun getCoordinatesByCity(q: String): CityResponse {
        return withContext(Dispatchers.IO) {
            coordinatesByCityApi.getCoordinatesResponse(
                cityName = q,
                language = getLang()
            )
        }
    }

    private fun getLang(): String = Locale.getDefault().language
}