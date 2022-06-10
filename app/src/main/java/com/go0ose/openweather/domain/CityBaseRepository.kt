package com.go0ose.openweather.domain

import com.go0ose.openweather.data.storage.entity.CityWeatherEntity

interface CityBaseRepository {

    suspend fun getSavedCities(): List<CityWeatherEntity>

    suspend fun saveCity(cityWeatherEntity: CityWeatherEntity)

    suspend fun deleteCity(cityWeatherEntity: CityWeatherEntity)

    suspend fun updateAllCityWeather(cityWeatherList: List<CityWeatherEntity>)

    suspend fun updateCityWeather(cityWeatherList: CityWeatherEntity)
}