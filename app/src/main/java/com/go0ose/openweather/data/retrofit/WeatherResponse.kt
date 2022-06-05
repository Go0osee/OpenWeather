package com.go0ose.openweather.data.retrofit

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    var cityName: String = "",
    var fullCityName: String = "",
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: String,
    @SerializedName("current")
    val current: Current,
    @SerializedName("hourly")
    val hourly: List<Hourly>,
    @SerializedName("daily")
    val daily: List<Daily>
)

data class Weather(
    @SerializedName("id")
    val id: String,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)

data class Current(
    @SerializedName("dt")
    val dt: String,
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String,
    @SerializedName("temp")
    val temp: String,
    @SerializedName("feels_like")
    val feelsLike: String,
    @SerializedName("pressure")
    val pressure: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("dew_point")
    val dewPoint: String,
    @SerializedName("uvi")
    val uvi: String,
    @SerializedName("clouds")
    val clouds: String,
    @SerializedName("visibility")
    val visibility: String,
    @SerializedName("wind_speed")
    val windSpeed: String,
    @SerializedName("wind_deg")
    val windDeg: String,
    @SerializedName("wind_gust")
    val windGust: String,
    @SerializedName("weather")
    val weather: List<Weather>
)

data class Hourly(
    @SerializedName("dt")
    val dt: String,
    @SerializedName("temp")
    val temp: String,
    @SerializedName("feels_like")
    val feels_like: String,
    @SerializedName("pressure")
    val pressure: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("dew_point")
    val dewPoint: String,
    @SerializedName("uvi")
    val uvi: String,
    @SerializedName("clouds")
    val clouds: String,
    @SerializedName("visibility")
    val visibility: String,
    @SerializedName("wind_speed")
    val wind_speed: String,
    @SerializedName("wind_deg")
    val windDeg: String,
    @SerializedName("wind_gust")
    val windGust: String,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("pop")
    val pop: String
)

data class Daily(
    @SerializedName("dt")
    val dt: String,
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String,
    @SerializedName("moonrise")
    val moonrise: String,
    @SerializedName("moonset")
    val moonset: String,
    @SerializedName("moon_phase")
    val moonPhase: String,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    @SerializedName("pressure")
    val pressure: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("dew_point")
    val dewPoint: String,
    @SerializedName("wind_speed")
    val windSpeed: String,
    @SerializedName("wind_deg")
    val windDeg: String,
    @SerializedName("wind_gust")
    val windGust: String,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("clouds")
    val clouds: String,
    @SerializedName("pop")
    val pop: String,
    @SerializedName("rain")
    val rain: String,
    @SerializedName("uvi")
    val uvi: String
)

data class Temp(
    @SerializedName("day")
    val day: String,
    @SerializedName("min")
    val min: String,
    @SerializedName("max")
    val max: String,
    @SerializedName("night")
    val night: String,
    @SerializedName("eve")
    val eve: String,
    @SerializedName("morn")
    val morn: String
)

data class FeelsLike(
    @SerializedName("day")
    val day: String,
    @SerializedName("night")
    val night: String,
    @SerializedName("eve")
    val eve: String,
    @SerializedName("morn")
    val morn: String
)