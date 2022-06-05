package com.go0ose.openweather.presentation.fragments.citybase.recycler

import com.go0ose.openweather.domain.model.CityWeatherFromDataBase

interface OnCityBaseClickListener {
    fun onItemClick(item: CityWeatherFromDataBase)
}