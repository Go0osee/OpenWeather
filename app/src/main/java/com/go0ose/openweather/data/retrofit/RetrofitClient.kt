package com.go0ose.openweather.data.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val WEATHER_URl = "https://api.openweathermap.org/data/2.5/"
    private const val CITY_URl = "https://api.mapbox.com/geocoding/v5/mapbox.places/"

    const val OPEN_WEATHER_API_KEY = "9e0ef7624d0e1d62b89931f0f4d716c8"
    const val MAPBOX_API_KEY =
        "pk.eyJ1IjoiZ28wb3NlIiwiYSI6ImNsM3JnMjA1MjBsdDUzaXBmeHV6d24zMjcifQ.BPumeEDizs7UrzKT-vMWFQ"

    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private fun getWeatherClient() = Retrofit.Builder()
        .baseUrl(WEATHER_URl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getWeatherApi(): WeatherApi = getWeatherClient().create(WeatherApi::class.java)

    private fun getCityClient() = Retrofit.Builder()
        .baseUrl(CITY_URl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getCityApi(): CityByCoordinatesApi =
        getCityClient().create(CityByCoordinatesApi::class.java)

    private fun getCoordinatesClient() = Retrofit.Builder()
        .baseUrl(CITY_URl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getCoordinatesApi(): CoordinatesByCityApi =
        getCoordinatesClient().create(CoordinatesByCityApi::class.java)
}