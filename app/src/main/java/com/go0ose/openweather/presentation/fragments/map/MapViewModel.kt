package com.go0ose.openweather.presentation.fragments.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.go0ose.openweather.domain.CityBaseInteractor
import com.go0ose.openweather.domain.WeatherInteractor
import com.go0ose.openweather.domain.model.CityCoordinates
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.utils.mapper.toMapWeather
import com.go0ose.openweather.utils.mapper.toNewCityWeatherFromDataBase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val weatherInteractor: WeatherInteractor,
    private val cityBaseInteractor: CityBaseInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapState())
    val uiState = _uiState.asStateFlow()

    fun accept(action: MapAction) {
        when (action) {
            is MapAction.OnMapClicked -> {
                loadWeather(action.latLng)
            }
            is MapAction.OnBackPressedClicked -> {
                _uiState.value = _uiState.value.copy(
                    isBackPressedClicked = true,
                    isError = false,
                    weather = MapWeather(),
                    openCityWeather = null
                )
            }
            is MapAction.OpenCityWeather -> {
                openCityWeather(action.weather)
            }
        }
    }

    private fun openCityWeather(weather: MapWeather) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isBackPressedClicked = false,
                isError = false,
                isLoading = true,
                weather = MapWeather(),
                openCityWeather = null
            )
            try {
                var cityWeather: CityWeatherFromDataBase? = null
                cityBaseInteractor.getAllCitiesCoordinatesFromBase()
                    .forEach { cityWeatherFromDataBase ->
                        if (cityWeatherFromDataBase.fullCityName == weather.fullCityName) {
                            cityWeather = cityWeatherFromDataBase
                        }
                    }

                if (cityWeather != null) {
                    _uiState.value = _uiState.value.copy(
                        isBackPressedClicked = false,
                        isError = false,
                        isLoading = false,
                        weather = MapWeather(),
                        openCityWeather = cityWeather
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isBackPressedClicked = false,
                        isError = false,
                        isLoading = false,
                        weather = MapWeather(),
                        openCityWeather = weatherInteractor.getWeather(
                            CityCoordinates(
                                lat = weather.lat,
                                lon = weather.lng
                            )
                        ).toNewCityWeatherFromDataBase()
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isBackPressedClicked = false,
                    isError = true,
                    isLoading = false,
                    weather = MapWeather(),
                    openCityWeather = null
                )
            }
        }
    }

    private fun loadWeather(latLng: LatLng) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                isError = false,
                weather = MapWeather(),
                openCityWeather = null
            )
            try {
                val mapWeather = weatherInteractor.getWeather(
                    CityCoordinates(
                        lat = latLng.latitude.toString(),
                        lon = latLng.longitude.toString()
                    )
                ).toMapWeather()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    weather = mapWeather.copy(
                        isWeather = true
                    ),
                    openCityWeather = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isError = true,
                    weather = MapWeather(),
                    openCityWeather = null
                )
            }
        }
    }
}