package com.go0ose.openweather.presentation.mainActivity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.go0ose.openweather.R
import com.go0ose.openweather.WeatherApplication
import com.go0ose.openweather.databinding.ActivityMainBinding
import com.go0ose.openweather.utils.ext.openFragment
import com.go0ose.openweather.presentation.fragments.weather.WeatherFragment
import kotlinx.coroutines.delay
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WeatherApplication.appComponent?.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        checkLocationPermission()
        openWeatherFragment()
    }

    private fun openWeatherFragment() {
        if (viewModel.isFirstLogin()) {
            viewModel.cityWeatherFromDataBase.observe(this) { cityWeatherFromDataBase ->
                viewModel.saveCityToBase(cityWeatherFromDataBase)
                openFragment(
                    WeatherFragment.newInstance(cityWeatherFromDataBase),
                    WeatherFragment.TAG,
                    R.id.container
                )
            }
            viewModel.getLastLocation(this)
        } else {
            viewModel.mainCities.observe(this) { cityCoordinatesFromBase ->
                openFragment(
                    WeatherFragment.newInstance(cityCoordinatesFromBase),
                    WeatherFragment.TAG,
                    R.id.container
                )
            }
            viewModel.getMainCity()
            viewModel.updateMainCityInBase()
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.fragments.isEmpty()) {
            finish()
        }
    }
}