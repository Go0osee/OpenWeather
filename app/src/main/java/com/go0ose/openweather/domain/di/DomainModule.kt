package com.go0ose.openweather.domain.di

import com.go0ose.openweather.data.WeatherRepositoryImpl
import com.go0ose.openweather.data.retrofit.CityByCoordinatesApi
import com.go0ose.openweather.data.retrofit.CoordinatesByCityApi
import com.go0ose.openweather.data.retrofit.WeatherApi
import com.go0ose.openweather.domain.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideWeatherInteractor(
        repository: WeatherRepository
    ): WeatherInteractor {
        return WeatherInteractorImpl(repository)
    }

    @Provides
    fun provideWeatherRepository(
        weatherApi: WeatherApi,
        cityByCoordinatesApi: CityByCoordinatesApi,
        coordinatesByCityApi: CoordinatesByCityApi
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi, cityByCoordinatesApi, coordinatesByCityApi)
    }

    @Provides
    fun provideCityBaseInteractor(
        repository: CityBaseRepository
    ): CityBaseInteractor {
        return CityBaseInteractorImpl(repository)
    }
}