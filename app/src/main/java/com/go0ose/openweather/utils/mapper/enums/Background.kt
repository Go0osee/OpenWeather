package com.go0ose.openweather.utils.mapper.enums

import androidx.annotation.DrawableRes
import com.go0ose.openweather.R

enum class Background(
    val tagIcon: String,
    @DrawableRes
    val imageId: Int
) {
    DAY01("01d", R.drawable.background_01d),
    NIGHT01("01n", R.drawable.background_01n),
    DAY02("02d", R.drawable.background_02d),
    NIGHT02("02n", R.drawable.background_02n),
    DAY03("03d", R.drawable.background_03d),
    NIGHT03("03n", R.drawable.background_03n),
    DAY04("04d", R.drawable.background_04d),
    NIGHT04("04n", R.drawable.background_04n),
    DAY09("09d", R.drawable.background_09d),
    NIGHT09("09n", R.drawable.background_09n),
    DAY10("10d", R.drawable.background_10d),
    NIGHT10("10n", R.drawable.background_10n),
    DAY11("11d", R.drawable.background_11d),
    NIGHT11("11n", R.drawable.background_11n),
    DAY13("13d", R.drawable.background_13d),
    NIGHT13("13n", R.drawable.background_13n),
    DAY50("50d", R.drawable.background_50d),
    NIGHT50("50n", R.drawable.background_50n)
}