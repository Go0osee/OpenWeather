package com.go0ose.openweather.domain.model

import androidx.annotation.DrawableRes

class HourlyItem(
    var time: String,
    @DrawableRes
    val iconHourly: Int,
    val temp: String
)