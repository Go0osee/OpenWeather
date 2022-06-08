package com.go0ose.openweather.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoordinatesByCityApi {

    @GET("{city}.json")
    suspend fun getCoordinatesResponse(
        @Path("city") cityName: String,
        @Query("access_token") accessToken: String = RetrofitClient.MAPBOX_API_KEY,
        @Query("language") language: String,
        @Query("types") types: String = "place",
        @Query("limit") limit: String = "10"
    ): CityResponse
}