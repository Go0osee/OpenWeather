package com.go0ose.openweather.presentation.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.go0ose.openweather.domain.CityBaseInteractor
import com.go0ose.openweather.domain.WeatherInteractor
import com.go0ose.openweather.utils.mapper.toCityCoordinates
import com.go0ose.openweather.utils.mapper.toNewCityWeatherFromDataBase
import com.go0ose.openweather.domain.model.CityItem
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.utils.prefs.SharedPreferenceManager
import kotlinx.coroutines.launch

class SearchViewModel(
    private val weatherInteractor: WeatherInteractor,
    private val cityBaseInteractor: CityBaseInteractor,
    private val prefs: SharedPreferenceManager
) : ViewModel() {

    companion object {
        private const val BACKGROUND_KEY = "BACKGROUND_KEY"
    }

    private val _cityItem = MutableLiveData<List<CityItem>>()
    val cityItem: LiveData<List<CityItem>> get() = _cityItem

    private val _cityWeather = MutableLiveData<CityWeatherFromDataBase>()
    val cityWeather: LiveData<CityWeatherFromDataBase> get() = _cityWeather

    fun searchCity(q: String){
        viewModelScope.launch {
            _cityItem.postValue(weatherInteractor.getCoordinatesByCity(q))
        }
    }

    fun openSearchWeather(item: CityItem) {
        viewModelScope.launch {
            var cityWeather: CityWeatherFromDataBase? = null
            cityBaseInteractor.getAllCitiesCoordinatesFromBase().forEach { cityWeatherFromDataBase ->
                if(cityWeatherFromDataBase.fullCityName == item.fullName) {
                    cityWeather = cityWeatherFromDataBase
                }
            }
            if (cityWeather != null) {
                _cityWeather.postValue(cityWeather)
            } else  {
                _cityWeather.postValue(weatherInteractor.getWeather(item.toCityCoordinates()).toNewCityWeatherFromDataBase())
            }
        }
    }
    fun loadBackgroundFromPrefs(): Int = prefs.getString(BACKGROUND_KEY).toInt()
}