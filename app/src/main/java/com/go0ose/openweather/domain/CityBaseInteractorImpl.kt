package com.go0ose.openweather.domain

import com.go0ose.openweather.utils.ext.toCityWeatherFromDataBase
import com.go0ose.openweather.utils.ext.toCityWeatherEntity
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase

class CityBaseInteractorImpl(
    private val repository: CityBaseRepository
) : CityBaseInteractor {
    override suspend fun addCityWeatherFromDataBase(cityWeatherFromDataBase: CityWeatherFromDataBase) {
        repository.saveCity(cityWeatherFromDataBase.toCityWeatherEntity())
    }

    override suspend fun getAllCitiesCoordinatesFromBase(): List<CityWeatherFromDataBase> {
        return repository.getSavedCities().map { cityWeatherEntity ->
            cityWeatherEntity.toCityWeatherFromDataBase()
        }
    }

    override suspend fun deleteCityCoordinatesFromBase(cityWeatherFromDataBase: CityWeatherFromDataBase) {
        repository.deleteCity(cityWeatherFromDataBase.toCityWeatherEntity())
    }

    override suspend fun updateAllCityWeather(cityWeatherListFromDataBase: List<CityWeatherFromDataBase>) {
        repository.updateAllCityWeather(cityWeatherListFromDataBase.map { cityWeatherFromDataBase ->
            cityWeatherFromDataBase.toCityWeatherEntity()
        })
    }

    override suspend fun updateCityWeather(cityWeatherFromDataBase: CityWeatherFromDataBase) {
        repository.updateCityWeather(cityWeatherFromDataBase.toCityWeatherEntity())
    }
}