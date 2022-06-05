package com.go0ose.openweather.data.retrofit

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("query")
    val query: List<String>,
    @SerializedName("features")
    val features: List<Features>
)

data class Features(
    @SerializedName("text")
    val text: String,
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("center")
    val center: List<String>
)
