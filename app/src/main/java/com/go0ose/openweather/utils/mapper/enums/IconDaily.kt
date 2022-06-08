package com.go0ose.openweather.utils.mapper.enums

import androidx.annotation.DrawableRes
import com.go0ose.openweather.R

enum class IconDaily(
    val tagIcon: String,
    @DrawableRes
    val imageId: Int
) {
    DAY01("01d", R.drawable.ic_daily_01d),
    NIGHT01("01n", R.drawable.ic_daily_01n),
    DAY02("02d", R.drawable.ic_daily_02d),
    NIGHT02("02n", R.drawable.ic_daily_02n),
    DAY03("03d", R.drawable.ic_daily_03d),
    NIGHT03("03n", R.drawable.ic_daily_03n),
    DAY04("04d", R.drawable.ic_daily_04d),
    NIGHT04("04n", R.drawable.ic_daily_04n),
    DAY09("09d", R.drawable.ic_daily_09d),
    NIGHT09("09n", R.drawable.ic_daily_09n),
    DAY10("10d", R.drawable.ic_daily_10d),
    NIGHT10("10n", R.drawable.ic_daily_10n),
    DAY11("11d", R.drawable.ic_daily_11d),
    NIGHT11("11n", R.drawable.ic_daily_11n),
    DAY13("13d", R.drawable.ic_daily_13d),
    NIGHT13("13n", R.drawable.ic_daily_13n),
    DAY50("50d", R.drawable.ic_daily_50d),
    NIGHT50("50n", R.drawable.ic_daily_50n)
}