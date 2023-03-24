package com.go0ose.openweather.utils.mapper

import com.go0ose.openweather.data.retrofit.*
import com.go0ose.openweather.data.storage.entity.CityWeatherEntity
import com.go0ose.openweather.domain.model.*
import com.go0ose.openweather.presentation.fragments.map.MapWeather
import com.go0ose.openweather.utils.AppConstants.ADDED_CITY_ID_FAVORITE
import com.go0ose.openweather.utils.AppConstants.MAIN_CITY_ID_FAVORITE
import com.go0ose.openweather.utils.AppConstants.NEW_CITY_ID_FAVORITE
import com.go0ose.openweather.utils.AppConstants.PRESSURE_INDEX
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
        pressure = (current.pressure.toDouble() * PRESSURE_INDEX).toInt().toString(),
        hourlyItems = hourly.toHourlyItems(timezoneOffset),
        dailyItems = daily.toDailyItems(timezoneOffset)
    )

fun WeatherWrapper.toMapWeather() =
    MapWeather(
        lat = lat,
        lng = lon,
        cityName = cityName,
        fullCityName = fullCityName,
        temp = temp,
        icon = mainIcon,
        feelsLike = fellsLike,
        description = description,
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
        favorite = MAIN_CITY_ID_FAVORITE
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
        favorite = ADDED_CITY_ID_FAVORITE
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
        favorite = NEW_CITY_ID_FAVORITE
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
        pressure = (pressure.toDouble() * PRESSURE_INDEX).toInt().toString(),
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

fun Date.toDailyDay() = SimpleDateFormat("EEEE").format(this)
    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

fun Date.toDailyDate() = SimpleDateFormat("d MMMM").format(this)

fun CityItem.toCityCoordinates() = CityCoordinates(lat, lon)