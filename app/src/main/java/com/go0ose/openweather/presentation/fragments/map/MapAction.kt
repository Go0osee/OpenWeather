package com.go0ose.openweather.presentation.fragments.map

import com.google.android.gms.maps.model.LatLng

sealed class MapAction {
    object OnBackPressedClicked : MapAction()
    class OnMapClicked(val latLng: LatLng) : MapAction()
    class OpenCityWeather(val weather: MapWeather) : MapAction()
}