package com.go0ose.openweather.domain

import androidx.room.Update
import com.go0ose.openweather.data.storage.entity.CityWeatherEntity
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase

interface CityBaseInteractor {
    suspend fun addCityWeatherFromDataBase(cityWeatherFromDataBase: CityWeatherFromDataBase)

    suspend fun getAllCitiesCoordinatesFromBase(): List<CityWeatherFromDataBase>

    suspend fun deleteCityCoordinatesFromBase(cityWeatherFromDataBase: CityWeatherFromDataBase)

    suspend fun updateAllCityWeather(cityWeatherListFromDataBase: List<CityWeatherFromDataBase>)

    suspend fun updateCityWeather(cityWeatherFromDataBase: CityWeatherFromDataBase)
}