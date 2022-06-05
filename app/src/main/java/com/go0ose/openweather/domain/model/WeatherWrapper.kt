package com.go0ose.openweather.domain.model

import androidx.annotation.DrawableRes
import com.go0ose.openweather.domain.model.DailyItem
import com.go0ose.openweather.domain.model.HourlyItem

class WeatherWrapper(
    var cityName: String,
    val fullCityName: String,
    val lat: String,
    val lon: String,
    val temp: String,
    @DrawableRes
    val background: Int,
    @DrawableRes
    val mainIcon: Int,
    val description: String,
    val fellsLike: String,
    val windSpeed: String,
    val humidity: String,
    val pressure: String,
    val hourlyItems: List<HourlyItem>,
    val dailyItems: List<DailyItem>
)