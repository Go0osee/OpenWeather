package com.go0ose.openweather.utils.mapper

import com.go0ose.openweather.R
import com.go0ose.openweather.data.retrofit.*
import com.go0ose.openweather.data.storage.entity.CityWeatherEntity
import com.go0ose.openweather.domain.model.*
import com.go0ose.openweather.utils.mapper.enums.Background
import com.go0ose.openweather.utils.mapper.enums.IconDaily
import com.go0ose.openweather.utils.mapper.enums.IconHourly
import com.go0ose.openweather.utils.mapper.enums.WingDeg
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

fun WeatherResponse.toWeatherWrapper() =
    WeatherWrapper(
        cityName = cityName,
        fullCityName = fullCityName,
        lat = lat,
        lon = lon,
        temp = current.temp.toTemp(),
        background = current.weather[0].icon.toBackground(),
        mainIcon = current.weather[0].icon.toIconDaily(),
        description = current.weather[0].description.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        },
        fellsLike = current.feelsLike.toFeelsLike(),
        windSpeed = current.windSpeed.toDouble().toInt().toString(),
        windDeg = current.windDeg.toWingDeg(),
        humidity = current.humidity + "%",
        pressure = (current.pressure.toDouble() * 0.7501).toInt().toString(),
        hourlyItems = hourly.toHourlyItems(timezoneOffset),
        dailyItems = daily.toDailyItems(timezoneOffset)
    )

fun WeatherWrapper.toCityWeatherFromDataBaseFirstLogin() =
    CityWeatherFromDataBase(
        cityName = cityName,
        fullCityName = fullCityName,
        lat = lat,
        lon = lon,
        temp = temp,
        background = background,
        mainIcon = mainIcon,
        description = description,
        fellsLike = fellsLike,
        favorite = 1
    )

fun WeatherWrapper.toCityWeatherFromDataBase() =
    CityWeatherFromDataBase(
        cityName = cityName,
        fullCityName = fullCityName,
        lat = lat,
        lon = lon,
        temp = temp,
        background = background,
        mainIcon = mainIcon,
        description = description,
        fellsLike = fellsLike,
        favorite = 2
    )

fun WeatherWrapper.toNewCityWeatherFromDataBase() =
    CityWeatherFromDataBase(
        cityName = cityName,
        fullCityName = fullCityName,
        lat = lat,
        lon = lon,
        temp = temp,
        background = background,
        mainIcon = mainIcon,
        description = description,
        fellsLike = fellsLike,
        favorite = 3
    )

fun CityWeatherFromDataBase.toCityWeatherEntity() =
    CityWeatherEntity(
        id = id,
        cityName = cityName,
        fullCityName = fullCityName,
        lat = lat,
        lon = lon,
        temp = temp,
        background = background,
        mainIcon = mainIcon,
        description = description,
        fellsLike = fellsLike,
        favorite = favorite,
    )

fun CityWeatherEntity.toCityWeatherFromDataBase() =
    CityWeatherFromDataBase(
        id = id,
        cityName = cityName,
        fullCityName = fullCityName,
        lat = lat,
        lon = lon,
        temp = temp,
        background = background,
        mainIcon = mainIcon,
        description = description,
        fellsLike = fellsLike,
        favorite = favorite,
    )

fun Features.toCityItem() =
    CityItem(
        name = text,
        fullName = placeName,
        lat = center[1],
        lon = center[0],
    )

fun Daily.toDailyItem(timezoneOffset: String) =
    DailyItem(
        date = dt.toDate().toDailyDate(),
        day = dt.toDate().toDailyDay(),
        iconDaily = weather[0].icon.toIconDaily(),
        tempMorn = temp.morn.toTemp(),
        tempDay = temp.day.toTemp(),
        tempEve = temp.eve.toTemp(),
        tempNight = temp.night.toTemp(),
        feelsLikeMorn = feelsLike.morn.toTemp(),
        feelsLikeDay = feelsLike.day.toTemp(),
        feelsLikeEve = feelsLike.eve.toTemp(),
        feelsLikeNight = feelsLike.night.toTemp(),
        pressure = (pressure.toDouble() * 0.7501).toInt().toString(),
        humidity = "$humidity%",
        windSpeed = windSpeed.toDouble().toInt().toString(),
        windDeg = windDeg.toWingDeg(),
        uvi = uvi.toUvi(),
        pop = pop.toPop(),
        moonPhase = moonPhase.toMoonPhase(),
        sunrise = sunrise.toHourlyTime(timezoneOffset),
        sunset = sunset.toHourlyTime(timezoneOffset),
    )

fun Hourly.toHourlyItems(timezoneOffset: String) =
    HourlyItem(
        iconHourly = weather[0].icon.toIconHourly(),
        temp = temp.toTemp(),
        time = dt.toHourlyTime(timezoneOffset)
    )

fun List<CityWeatherFromDataBase>.toFirstFavorite(): List<CityWeatherFromDataBase> =
    this.sortedBy { it.favorite }

fun CityWeatherFromDataBase.toCityCoordinates() = CityCoordinates(lat, lon)

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

fun List<Daily>.toDailyItems(timezoneOffset: String): List<DailyItem> {
    return this.map { daily ->
        daily.toDailyItem(timezoneOffset)
    }

}

fun List<Hourly>.toHourlyItems(timezoneOffset: String): List<HourlyItem> {
    val listHourly = mutableListOf<HourlyItem>()
    for (i in 0..22) {
        listHourly.add(this[i].toHourlyItems(timezoneOffset))
    }
    return listHourly
}

fun String.toDate() = Date(this.toLong() * 1000)

fun String.toHourlyTime(timezoneOffset: String) =
    SimpleDateFormat("HH:mm").format(Date(this.toLong() * 1000 - TimeZone.getDefault().rawOffset + timezoneOffset.toLong() * 1000))

fun Date.toDailyDay() = SimpleDateFormat("EEEE").format(this)
    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

fun Date.toDailyDate() = SimpleDateFormat("d MMMM").format(this)

fun CityItem.toCityCoordinates() = CityCoordinates(lat, lon)

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
        else -> throw IllegalStateException("Wrong type")
    }
}

fun String.toUvi(): Uvi {
    return when (this.toDouble().toInt()) {
        in 0..2 -> Uvi(this.toDouble().toInt(), R.string.low)
        in 3..5 -> Uvi(this.toDouble().toInt(), R.string.moderate)
        in 6..7 -> Uvi(this.toDouble().toInt(), R.string.high)
        in 8..10 -> Uvi(this.toDouble().toInt(), R.string.very_high)
        in 11..Int.MAX_VALUE -> Uvi(this.toDouble().toInt(), R.string.extreme)
        else -> throw IllegalStateException("Wrong type")
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
        else -> throw IllegalStateException("Wrong type")
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