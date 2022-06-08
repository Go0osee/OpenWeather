package com.go0ose.openweather.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CityByCoordinatesApi {

    @GET("{longitude},{latitude}.json")
    suspend fun getCityResponse(
        @Path("latitude") lat: String,
        @Path("longitude") lon: String,
        @Query("access_token") accessToken: String = RetrofitClient.MAPBOX_API_KEY,
        @Query("limit") limit: String = "1",
        @Query("language") language: String,
        @Query("types") types: String = "place",
        ): CityResponse
}