package com.go0ose.openweather.presentation.di

import android.content.Context
import com.go0ose.openweather.domain.CityBaseInteractor
import com.go0ose.openweather.domain.WeatherInteractor
import com.go0ose.openweather.presentation.fragments.citybase.CityBaseViewModel
import com.go0ose.openweather.presentation.fragments.search.SearchViewModel
import com.go0ose.openweather.presentation.fragments.weather.WeatherViewModel
import com.go0ose.openweather.presentation.mainActivity.MainViewModel
import com.go0ose.openweather.utils.prefs.SharedPreferenceManager
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideWeatherViewModel(
        weatherInteractor: WeatherInteractor,
        cityBaseInteractor: CityBaseInteractor,
        prefs: SharedPreferenceManager
    ): WeatherViewModel {
        return WeatherViewModel(weatherInteractor, cityBaseInteractor, prefs)
    }

    @Provides
    fun provideMainViewModel(
        cityBaseInteractor: CityBaseInteractor,
        weatherInteractor: WeatherInteractor,
        prefs: SharedPreferenceManager
    ): MainViewModel {
        return MainViewModel(cityBaseInteractor, weatherInteractor, prefs)
    }

    @Provides
    fun provideCityBaseViewModel(
        cityBaseInteractor: CityBaseInteractor,
        weatherInteractor: WeatherInteractor,
        prefs: SharedPreferenceManager
    ): CityBaseViewModel {
        return CityBaseViewModel(cityBaseInteractor, weatherInteractor, prefs)
    }

    @Provides
    fun provideSearchViewModel(
        weatherInteractor: WeatherInteractor,
        cityBaseInteractor: CityBaseInteractor,
        prefs: SharedPreferenceManager
    ): SearchViewModel {
        return SearchViewModel(weatherInteractor, cityBaseInteractor, prefs)
    }
}