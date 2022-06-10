package com.go0ose.openweather.utils.mapper

import com.go0ose.openweather.R
import com.go0ose.openweather.domain.model.MoonPhase
import com.go0ose.openweather.domain.model.Uvi
import com.go0ose.openweather.utils.AppConstants.DATE_INDEX
import com.go0ose.openweather.utils.AppConstants.WRONG_TYPE
import com.go0ose.openweather.utils.mapper.enums.Background
import com.go0ose.openweather.utils.mapper.enums.IconDaily
import com.go0ose.openweather.utils.mapper.enums.IconHourly
import com.go0ose.openweather.utils.mapper.enums.WingDeg
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

fun String.toWingDeg(): WingDeg {
    return when (this.toInt()) {
        in 22..67 -> WingDeg.NE
        in 68..112 -> WingDeg.E
        in 113..157 -> WingDeg.SE
        in 158..202 -> WingDeg.S
        in 203..247 -> WingDeg.SW
        in 248..292 -> WingDeg.W
        in 293..337 -> WingDeg.NW
        in 337..360 -> WingDeg.N
        in 0..21 -> WingDeg.N
        else -> throw IllegalStateException(WRONG_TYPE)
    }
}

fun String.toUvi(): Uvi {
    return when (this.toDouble().toInt()) {
        in 0..2 -> Uvi(this.toDouble().toInt(), R.string.low)
        in 3..5 -> Uvi(this.toDouble().toInt(), R.string.moderate)
        in 6..7 -> Uvi(this.toDouble().toInt(), R.string.high)
        in 8..10 -> Uvi(this.toDouble().toInt(), R.string.very_high)
        in 11..Int.MAX_VALUE -> Uvi(this.toDouble().toInt(), R.string.extreme)
        else -> throw IllegalStateException(WRONG_TYPE)
    }
}

fun String.toMoonPhase(): MoonPhase {
    return when (this.toDouble()) {
        in 0.96..1.0 -> MoonPhase.NEW_MOON
        in 0.0..0.04 -> MoonPhase.NEW_MOON
        in 0.05..0.21 -> MoonPhase.GROWING_MONTH
        in 0.22..0.29 -> MoonPhase.FIRST_QUARTER
        in 0.30..0.46 -> MoonPhase.GROWING_MOON
        in 0.47..0.54 -> MoonPhase.FULL_MOON
        in 0.55..0.71 -> MoonPhase.WANING_MOON
        in 0.72..0.79 -> MoonPhase.THIRD_QUARTER
        in 0.80..0.95 -> MoonPhase.WANING_MONTH
        else -> throw IllegalStateException(WRONG_TYPE)
    }
}

fun String.toIconDaily(): Int {
    var id = 0
    for (icon in IconDaily.values()) {
        if (icon.tagIcon == this) {
            id = icon.imageId
        }
    }
    return id
}

fun String.toIconHourly(): Int {
    var id = 0
    for (icon in IconHourly.values()) {
        if (icon.tagIcon == this) {
            id = icon.imageId
        }
    }
    return id
}

fun String.toBackground(): Int {
    var id = 0
    for (icon in Background.values()) {
        if (icon.tagIcon == this) {
            id = icon.imageId
        }
    }
    return id
}

fun String.toDate() = Date(this.toLong() * DATE_INDEX)

fun String.toHourlyTime(timezoneOffset: String) =
    SimpleDateFormat("HH:mm").format(Date(this.toLong() * DATE_INDEX - TimeZone.getDefault().rawOffset + timezoneOffset.toLong() * DATE_INDEX))

fun String.toTemp(): String {
    return if (this.startsWith("-") || this.startsWith("0")) {
        this.toDouble().toInt().toString() + "°"
    } else {
        "+" + this.toDouble().toInt().toString() + "°"
    }
}

fun String.toPop(): String = (this.toDouble() * 100).toInt().toString() + "%"

fun String.toFeelsLike(): String {
    return if (this.startsWith("-") || this.startsWith("0")) {
        this.toDouble().toInt().toString() + "°"
    } else {
        "+" + this.toDouble().toInt().toString() + "°"
    }
}