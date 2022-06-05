package com.go0ose.openweather.data

import com.go0ose.openweather.data.storage.CityWeatherDao
import com.go0ose.openweather.data.storage.entity.CityWeatherEntity
import com.go0ose.openweather.domain.CityBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityBaseRepositoryImpl(
    private val cityWeatherDao: CityWeatherDao
): CityBaseRepository {
    override suspend fun getSavedCities(): List<CityWeatherEntity> {
        return withContext(Dispatchers.IO) {
            cityWeatherDao.getAllCityWeather()
        }
    }

    override suspend fun saveCity(cityWeatherEntity: CityWeatherEntity) {
        return withContext(Dispatchers.IO) {
            cityWeatherDao.saveCityWeather(cityWeatherEntity)
        }
    }

    override suspend fun deleteCity(cityWeatherEntity: CityWeatherEntity) {
        return withContext(Dispatchers.IO) {
            cityWeatherDao.deleteCityWeather(cityWeatherEntity)
        }
    }

    override suspend fun updateAllCityWeather(cityWeatherList: List<CityWeatherEntity>) {
        return withContext(Dispatchers.IO) {
            cityWeatherDao.updateAllCityWeather(cityWeatherList)
        }
    }

    override suspend fun updateCityWeather(cityWeatherList: CityWeatherEntity) {
        return withContext(Dispatchers.IO) {
            cityWeatherDao.updateCityWeather(cityWeatherList)
        }
    }


}