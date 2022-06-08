package com.go0ose.openweather.domain.model

import androidx.annotation.DrawableRes
import android.os.Parcelable
import androidx.annotation.StringRes
import com.go0ose.openweather.R
import com.go0ose.openweather.utils.mapper.enums.WingDeg
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
    val windDeg: WingDeg,
    val uvi: Uvi,
    val pop: String,
    val moonPhase: MoonPhase,
) : Parcelable

@Parcelize
class Uvi(
    val index: Int,
    @StringRes
    val description: Int
) : Parcelable

@Parcelize
enum class MoonPhase(
    @StringRes
    val description: Int,
    @DrawableRes
    val icon: Int
) : Parcelable {
    NEW_MOON(R.string.new_moon, R.drawable.ic_daily_moon_1),
    GROWING_MONTH(R.string.growing_month, R.drawable.ic_daily_moon_2),
    FIRST_QUARTER(R.string.first_quarter, R.drawable.ic_daily_moon_3),
    GROWING_MOON(R.string.growing_moon, R.drawable.ic_daily_moon_4),
    FULL_MOON(R.string.full_moon, R.drawable.ic_daily_moon_5),
    WANING_MOON(R.string.waning_moon, R.drawable.ic_daily_moon_6),
    THIRD_QUARTER(R.string.third_quarter, R.drawable.ic_daily_moon_7),
    WANING_MONTH(R.string.waning_month, R.drawable.ic_daily_moon_8)
}