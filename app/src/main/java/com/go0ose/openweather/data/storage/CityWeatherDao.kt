package com.go0ose.openweather.data.storage

import androidx.room.*
import com.go0ose.openweather.data.storage.entity.CityWeatherEntity

@Dao
interface CityWeatherDao {
    @Query("SELECT * FROM city_weather")
    suspend fun getAllCityWeather(): List<CityWeatherEntity>

    @Insert
    suspend fun saveCityWeather(cityWeather: CityWeatherEntity)

    @Delete
    suspend fun deleteCityWeather(cityWeather: CityWeatherEntity)

    @Update
    suspend fun updateAllCityWeather(cityWeatherList: List<CityWeatherEntity>)

    @Update
    suspend fun updateCityWeather(cityWeather: CityWeatherEntity)
}
