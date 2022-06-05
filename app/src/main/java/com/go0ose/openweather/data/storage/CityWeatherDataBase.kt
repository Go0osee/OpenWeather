package com.go0ose.openweather.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.go0ose.openweather.data.storage.entity.CityWeatherEntity

@Database(
    entities = [CityWeatherEntity::class],
    version = CityWeatherDataBase.VERSION
)
abstract class CityWeatherDataBase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun getCityWeatherDao(): CityWeatherDao
}
