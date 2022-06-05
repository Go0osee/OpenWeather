package com.go0ose.openweather.domain.model

import androidx.annotation.DrawableRes
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityWeatherFromDataBase(
    var id: Long = 0,
    val cityName: String,
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
    var favorite: Int,
) : Parcelable