package com.go0ose.openweather.utils.ext

import com.go0ose.openweather.R
import com.go0ose.openweather.data.retrofit.*
import com.go0ose.openweather.data.storage.entity.CityWeatherEntity
import com.go0ose.openweather.domain.model.*
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
        description = current.weather[0].description.capitalize(),  // fixme
        fellsLike = current.feelsLike.toFeelsLike(),
        windSpeed = current.windSpeed.toDouble().toInt()
            .toString() + " м/с, " + current.windDeg.toWingDeg(),
        humidity = current.humidity + "%",
        pressure = (current.pressure.toDouble() * 0.7501).toInt().toString() + " мм рт. ст.",
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
        pressure = (pressure.toDouble() * 0.7501).toInt().toString() + " мм рт. ст.",
        humidity = humidity + "%",
        windSpeed = windSpeed.toDouble().toInt().toString() + " м/с, " + windDeg.toWingDeg(),
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
    }  // fixme
}

fun String.toFeelsLike(): String {
    return if (this.startsWith("-") || this.startsWith("0")) {
        "Ощущается как: " + this.toDouble().toInt().toString() + "°"
    } else {
        "Ощущается как: +" + this.toDouble().toInt().toString() + "°"
    }
}

fun String.toPop(): String = (this.toDouble() * 100).toInt().toString() + "%"

fun List<Daily>.toDailyItems(timezoneOffset: String): List<DailyItem> {
    var listDaily = listOf<DailyItem>()
    listDaily = this.map { daily ->
        daily.toDailyItem(timezoneOffset)
    }
    listDaily[0].day = "Сегодня"
    listDaily[1].day = "Завтра"
    return listDaily
}

fun List<Hourly>.toHourlyItems(timezoneOffset: String): List<HourlyItem> {
    val listHourly = mutableListOf<HourlyItem>()
    for (i in 0..22) {
        listHourly.add(this[i].toHourlyItems(timezoneOffset))
    }
    listHourly[0].time = "сейчас"
    return listHourly
}

fun String.toDate() = Date(this.toLong() * 1000)

fun String.toHourlyTime(timezoneOffset: String) =
    SimpleDateFormat("HH:mm").format(Date(this.toLong() * 1000 - TimeZone.getDefault().rawOffset + timezoneOffset.toLong() * 1000))

fun Date.toDailyDay() = SimpleDateFormat("EEEE").format(this).capitalize()

fun Date.toDailyDate() = SimpleDateFormat("d MMMM").format(this)

fun CityItem.toCityCoordinates() =
    CityCoordinates(lat, lon)

fun String.toWingDeg(): String {
    return when (this.toInt()) {
        in 22..67 -> "СВ"
        in 68..112 -> "В"
        in 113..157 -> "ЮВ"
        in 158..202 -> "Ю"
        in 203..247 -> "ЮЗ"
        in 248..292 -> "З"
        in 293..337 -> "СЗ"
        in 337..360 -> "С"
        in 0..21 -> "С"
        else -> throw IllegalStateException("Wrong type")
    }
}

fun String.toUvi(): String {
    return this.toDouble().toInt().toString() + ", " + when (this.toDouble().toInt()) {
        in 0..2 -> "низкий"
        in 3..5 -> "умеренный"
        in 6..7 -> "высокий"
        in 8..10 -> "очень высокий"
        in 11..Int.MAX_VALUE -> "экстремальный"
        else -> throw IllegalStateException("Wrong type")
    }
}

fun String.toMoonPhase(): MoonPhase {
    return when (this.toDouble()) {
        in 0.96..1.0 -> MoonPhase("Новолуние", R.drawable.ic_daily_moon_1)
        in 0.0..0.04 -> MoonPhase("Новолуние", R.drawable.ic_daily_moon_1)
        in 0.05..0.21 -> MoonPhase("Растущий месяц", R.drawable.ic_daily_moon_2)
        in 0.22..0.29 -> MoonPhase("Первая четверть", R.drawable.ic_daily_moon_3)
        in 0.30..0.46 -> MoonPhase("Растущая луна", R.drawable.ic_daily_moon_4)
        in 0.47..0.54 -> MoonPhase("Полнолуние", R.drawable.ic_daily_moon_5)
        in 0.55..0.71 -> MoonPhase("Убывающая луна", R.drawable.ic_daily_moon_6)
        in 0.72..0.79 -> MoonPhase("Третья четверть", R.drawable.ic_daily_moon_7)
        in 0.80..0.95 -> MoonPhase("Убывающий месяц", R.drawable.ic_daily_moon_8)
        else -> throw IllegalStateException("Wrong type")
    }
}

fun String.toIconDaily(): Int {
    return when (this) {
        "01d" -> R.drawable.ic_daily_01d
        "01n" -> R.drawable.ic_daily_01n
        "02d" -> R.drawable.ic_daily_02d
        "02n" -> R.drawable.ic_daily_02n
        "03d" -> R.drawable.ic_daily_03d
        "03n" -> R.drawable.ic_daily_03n
        "04d" -> R.drawable.ic_daily_04d
        "04n" -> R.drawable.ic_daily_04n
        "09d" -> R.drawable.ic_daily_09d
        "09n" -> R.drawable.ic_daily_09n
        "10d" -> R.drawable.ic_daily_10d
        "10n" -> R.drawable.ic_daily_10n
        "11d" -> R.drawable.ic_daily_11d
        "11n" -> R.drawable.ic_daily_11n
        "13d" -> R.drawable.ic_daily_13d
        "13n" -> R.drawable.ic_daily_13n
        "50d" -> R.drawable.ic_daily_50d
        "50n" -> R.drawable.ic_daily_50n
        else -> throw IllegalStateException("Wrong type")
    }
}

fun String.toIconHourly(): Int {
    return when (this) {
        "01d" -> R.drawable.ic_hour_01d
        "01n" -> R.drawable.ic_hour_01n
        "02d" -> R.drawable.ic_hour_02d
        "02n" -> R.drawable.ic_hour_02n
        "03d" -> R.drawable.ic_hour_03d
        "03n" -> R.drawable.ic_hour_03n
        "04d" -> R.drawable.ic_hour_04d
        "04n" -> R.drawable.ic_hour_04n
        "09d" -> R.drawable.ic_hour_09d
        "09n" -> R.drawable.ic_hour_09n
        "10d" -> R.drawable.ic_hour_10d
        "10n" -> R.drawable.ic_hour_10n
        "11d" -> R.drawable.ic_hour_11d
        "11n" -> R.drawable.ic_hour_11n
        "13d" -> R.drawable.ic_hour_13d
        "13n" -> R.drawable.ic_hour_13n
        "50d" -> R.drawable.ic_hour_50d
        "50n" -> R.drawable.ic_hour_50n
        else -> throw IllegalStateException("Wrong type")
    }
}

fun String.toBackground(): Int {
    return when (this) {
        "01d" -> R.drawable.background_01d
        "01n" -> R.drawable.background_01n
        "02d" -> R.drawable.background_02d
        "02n" -> R.drawable.background_02n
        "03d" -> R.drawable.background_03d
        "03n" -> R.drawable.background_03n
        "04d" -> R.drawable.background_04d
        "04n" -> R.drawable.background_04n
        "09d" -> R.drawable.background_09d
        "09n" -> R.drawable.background_09n
        "10d" -> R.drawable.background_10d
        "10n" -> R.drawable.background_10n
        "11d" -> R.drawable.background_11d
        "11n" -> R.drawable.background_11n
        "13d" -> R.drawable.background_13d
        "13n" -> R.drawable.background_13n
        "50d" -> R.drawable.background_50d
        "50n" -> R.drawable.background_50n
        else -> throw IllegalStateException("Wrong type")
    }
}