package com.go0ose.openweather.utils.mapper.enums

import androidx.annotation.DrawableRes
import com.go0ose.openweather.R

enum class IconHourly(
    val tagIcon: String,
    @DrawableRes
    val imageId: Int
) {
    DAY01("01d", R.drawable.ic_hour_01d),
    NIGHT01("01n", R.drawable.ic_hour_01n),
    DAY02("02d", R.drawable.ic_hour_02d),
    NIGHT02("02n", R.drawable.ic_hour_02n),
    DAY03("03d", R.drawable.ic_hour_03d),
    NIGHT03("03n", R.drawable.ic_hour_03n),
    DAY04("04d", R.drawable.ic_hour_04d),
    NIGHT04("04n", R.drawable.ic_hour_04n),
    DAY09("09d", R.drawable.ic_hour_09d),
    NIGHT09("09n", R.drawable.ic_hour_09n),
    DAY10("10d", R.drawable.ic_hour_10d),
    NIGHT10("10n", R.drawable.ic_hour_10n),
    DAY11("11d", R.drawable.ic_hour_11d),
    NIGHT11("11n", R.drawable.ic_hour_11n),
    DAY13("13d", R.drawable.ic_hour_13d),
    NIGHT13("13n", R.drawable.ic_hour_13n),
    DAY50("50d", R.drawable.ic_hour_50d),
    NIGHT50("50n", R.drawable.ic_hour_50n)
}
