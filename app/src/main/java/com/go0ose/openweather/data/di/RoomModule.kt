package com.go0ose.openweather.data.di

import android.content.Context
import androidx.room.Room
import com.go0ose.openweather.data.storage.CityWeatherDao
import com.go0ose.openweather.data.storage.CityWeatherDataBase
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    fun provideCityWeatherDataBase(
        context: Context
    ): CityWeatherDataBase {
        return Room.databaseBuilder(
            context,
            CityWeatherDataBase::class.java,
            "cityWeather"
        ).build()
    }

    @Provides
    fun provideCityWeatherDao(
        cityWeatherDataBase: CityWeatherDataBase
    ): CityWeatherDao {
        return cityWeatherDataBase.getCityWeatherDao()
    }
}