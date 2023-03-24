package com.go0ose.openweather.presentation.fragments.map

import com.go0ose.openweather.domain.model.CityWeatherFromDataBase

data class MapState(
    var isLoading: Boolean = false,
    var weather: MapWeather = MapWeather(),
    var isBackPressedClicked: Boolean = false,
    var isError: Boolean = false,
    var openCityWeather: CityWeatherFromDataBase? = null
)

data class MapWeather(
    var isWeather: Boolean = false,
    var lat: String = "",
    var lng: String = "",
    var cityName: String = "",
    var fullCityName: String = "",
    var icon: Int = 0,
    var temp: String = "",
    var feelsLike: String = "",
    var description: String = ""
)
