package com.go0ose.openweather.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("onecall")
    suspend fun getWeatherResponse(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid") appid: String = RetrofitClient.OPEN_WEATHER_API_KEY,
        @Query("exclude") exclude: String = "minutely,alerts",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String
    ): WeatherResponse
}