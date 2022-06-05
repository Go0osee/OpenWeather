package com.go0ose.openweather.domain.model

import androidx.annotation.DrawableRes
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DailyItem(
    val date: String,
    var day: String,
    @DrawableRes
    val iconDaily: Int,
    val tempMorn: String,
    val tempDay: String,
    val tempEve: String,
    val tempNight: String,
    val feelsLikeMorn: String,
    val feelsLikeDay: String,
    val feelsLikeEve: String,
    val feelsLikeNight: String,
    val sunrise: String,
    val sunset: String,
    val pressure: String,
    val humidity: String,
    val windSpeed: String,
    val uvi: String,
    val pop: String,
    val moonPhase: MoonPhase,
) : Parcelable

@Parcelize
class MoonPhase(
    val name: String,
    @DrawableRes
    val icon: Int
): Parcelable