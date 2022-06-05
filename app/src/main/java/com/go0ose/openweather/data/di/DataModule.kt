package com.go0ose.openweather.data.di

import com.go0ose.openweather.data.CityBaseRepositoryImpl
import com.go0ose.openweather.data.retrofit.CityByCoordinatesApi
import com.go0ose.openweather.data.retrofit.CoordinatesByCityApi
import com.go0ose.openweather.data.retrofit.RetrofitClient
import com.go0ose.openweather.data.retrofit.WeatherApi
import com.go0ose.openweather.data.storage.CityWeatherDao
import com.go0ose.openweather.domain.CityBaseRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideWeatherApi(): WeatherApi {
        return RetrofitClient.getWeatherApi()
    }

    @Provides
    fun provideCityApi(): CityByCoordinatesApi {
        return RetrofitClient.getCityApi()
    }


    @Provides
    fun provideCoordinatesByCityApi(): CoordinatesByCityApi {
        return RetrofitClient.getCoordinatesApi()
    }

    @Provides
    fun provideDataBaseRepository(
        cityWeatherDao: CityWeatherDao
    ): CityBaseRepository {
        return CityBaseRepositoryImpl(cityWeatherDao)
    }
}