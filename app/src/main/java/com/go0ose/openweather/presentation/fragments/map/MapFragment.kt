package com.go0ose.openweather.presentation.fragments.map

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.go0ose.openweather.R
import com.go0ose.openweather.WeatherApplication
import com.go0ose.openweather.databinding.FragmentMapBinding
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.presentation.fragments.weather.WeatherFragment
import com.go0ose.openweather.utils.ext.openFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.TileOverlay
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private val binding: FragmentMapBinding by viewBinding()

    private lateinit var googleMap: GoogleMap
    private var tileType: String = TEMP
    private var tileOver: TileOverlay? = null

    @Inject
    lateinit var viewModel: MapViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WeatherApplication.appComponent?.inject(this)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initViews()
        initObservers()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        this.googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map)
        )
        this.googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        this.googleMap.setOnMapClickListener { latLng ->
            viewModel.accept(MapAction.OnMapClicked(latLng))
        }
        tileOver = this.googleMap.addTileOverlay(
            TileOverlayOptions().tileProvider(
                TileOWM(tileType)
            )
        )
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { state ->
                onBackPressed(state.isBackPressedClicked)
                showError(state.isError)
                showLoading(state.isLoading)
                showWeather(state.weather)
                openCityWeather(state.openCityWeather)
            }
        }
    }

    private fun openCityWeather(cityWeather: CityWeatherFromDataBase?) {
        if (cityWeather != null){
            requireActivity().openFragment(
                WeatherFragment.newInstance(cityWeather),
                WeatherFragment.TAG,
                R.id.container
            )
        }
    }

    private fun initViews() {
        val tileName = arrayOf(
            getString(R.string.temperature),
            getString(R.string.clouds),
            getString(R.string.precipitations),
            getString(R.string.pressure),
            getString(R.string.wind_speed)
        )

        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, tileName)
        adapter.setDropDownViewResource(R.layout.item_spinner)

        with(binding) {
            spinnerTileType.adapter = adapter
            spinnerTileType.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        when (position) {
                            0 -> tileType = TEMP
                            1 -> tileType = CLOUDS
                            2 -> tileType = PRECIPITATION
                            3 -> tileType = PRESSURE
                            4 -> tileType = WIND
                        }
                        tileOver?.remove()
                        setMapLayer()
                    }
                }

            buttonBack.setOnClickListener {
                viewModel.accept(MapAction.OnBackPressedClicked)
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showWeather(weather: MapWeather) {
        if (weather.isWeather) {
            val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_LONG)
            val customSnackView: View = layoutInflater.inflate(R.layout.snackbar_weather, null)
            snackbar.view.setBackgroundColor(Color.TRANSPARENT)
            val snackbarLayout = snackbar.view as SnackbarLayout
            snackbarLayout.setPadding(12, 8, 8, 12)
            val cityName: TextView = customSnackView.findViewById(R.id.cityName)
            val description: TextView = customSnackView.findViewById(R.id.description)
            val weatherImage: ImageView = customSnackView.findViewById(R.id.weatherImage)
            val feelsLike: TextView = customSnackView.findViewById(R.id.feelsLike)
            val temp: TextView = customSnackView.findViewById(R.id.temp)
            val card: CardView = customSnackView.findViewById(R.id.card)

            cityName.text = weather.cityName
            description.text = weather.description
            weatherImage.setImageResource(weather.icon)
            feelsLike.text = "${getString(R.string.feels_like)}${weather.feelsLike}"
            temp.text = weather.temp

            card.setOnClickListener {
                viewModel.accept(MapAction.OpenCityWeather(weather))
            }

            snackbarLayout.addView(customSnackView, 0)
            snackbar.show()
        }
    }

    private fun showError(error: Boolean) {
        if (error) {
            Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onBackPressed(isClicked: Boolean) {
        if (isClicked) {
            requireActivity().onBackPressed()
        }
    }

    private fun setMapLayer() {
        tileOver = googleMap.addTileOverlay(
            TileOverlayOptions().tileProvider(
                TileOWM(tileType)
            )
        )
    }

    companion object {
        const val TAG = "MapFragment"
        const val TEMP = "temp_new"
        const val CLOUDS = "clouds_new"
        const val PRECIPITATION = "precipitation_new"
        const val PRESSURE = "pressure_new"
        const val WIND = "wind_new"

        fun newInstance() = MapFragment()
    }
}