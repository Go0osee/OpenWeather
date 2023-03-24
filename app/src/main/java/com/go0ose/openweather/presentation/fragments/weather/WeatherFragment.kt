package com.go0ose.openweather.presentation.fragments.weather

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.go0ose.openweather.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.go0ose.openweather.WeatherApplication
import com.go0ose.openweather.databinding.FragmentWeatherBinding
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.domain.model.DailyItem
import com.go0ose.openweather.utils.ext.openFragmentBase
import com.go0ose.openweather.presentation.fragments.citybase.CityBaseFragment
import com.go0ose.openweather.presentation.fragments.daily.DailyBottomSheetFragment
import com.go0ose.openweather.presentation.fragments.map.MapFragment
import com.go0ose.openweather.presentation.fragments.weather.recyclers.DailyAdapter
import com.go0ose.openweather.presentation.fragments.weather.recyclers.HourlyAdapter
import com.go0ose.openweather.presentation.fragments.weather.recyclers.OnDailyItemClickListener
import com.go0ose.openweather.utils.AppConstants.WEATHER_KEY
import com.go0ose.openweather.utils.ext.ifNetworkUnavailable
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class WeatherFragment() : Fragment(R.layout.fragment_weather) {

    companion object {
        const val TAG = "WeatherFragment"

        fun newInstance(cityWeatherFromDataBase: CityWeatherFromDataBase) = WeatherFragment()
            .apply {
                arguments = Bundle().apply { putParcelable(WEATHER_KEY, cityWeatherFromDataBase) }
            }
    }

    private val binding: FragmentWeatherBinding by viewBinding()

    @Inject
    lateinit var viewModel: WeatherViewModel
    private val adapterHourly by lazy { HourlyAdapter() }
    private val adapterDaily by lazy { DailyAdapter(onDailyItemClickListener) }

    private val cityWeatherFromDataBase: CityWeatherFromDataBase by lazy {
        arguments?.getParcelable(WEATHER_KEY)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WeatherApplication.appComponent?.inject(this)

        binding.root.ifNetworkUnavailable {
            Toast.makeText(
                requireContext(),
                getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.loadWeather(cityWeatherFromDataBase)
        initViews()
        initObserver()
    }

    private fun initViews() {
        with(binding) {

            when (cityWeatherFromDataBase.favorite) {
                2 -> {
                    addOrFavoriteImage.setImageResource(R.drawable.ic_main_favourite)
                    addOrFavorite.setOnClickListener {
                        cityWeatherFromDataBase.favorite = 1

                        viewModel.changeFavorite(cityWeatherFromDataBase)
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.now_it_is_the_main_city),
                            Toast.LENGTH_SHORT
                        ).show()

                        addOrFavoriteImage.setImageResource(0)
                        initViews()
                    }
                }
                3 -> {
                    addOrFavoriteImage.setImageResource(R.drawable.ic_main_plus)
                    addOrFavorite.setOnClickListener {
                        cityWeatherFromDataBase.favorite = 2
                        viewModel.addToDataBase(cityWeatherFromDataBase)
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.added),
                            Toast.LENGTH_SHORT
                        ).show()
                        initViews()
                    }
                }
            }

            map.setOnClickListener {
                requireActivity().openFragmentBase(
                    MapFragment.newInstance(),
                    MapFragment.TAG,
                    R.id.container
                )
            }

            hourlyRecycler.adapter = adapterHourly
            dailyRecycler.adapter = adapterDaily

            background.setImageResource(cityWeatherFromDataBase.background)
            cityName.text = cityWeatherFromDataBase.cityName
            temperature.text = cityWeatherFromDataBase.temp
            mainIcon.setImageResource(cityWeatherFromDataBase.mainIcon)
            cloudCover.text = cityWeatherFromDataBase.description
            feelsLike.text = cityWeatherFromDataBase.fellsLike
        }
    }

    private fun initObserver() {
        with(binding) {
            viewModel.weatherWrapper.observe(viewLifecycleOwner) { weatherWrapper ->

                viewModel.loadBackgroundToPref(weatherWrapper.background)
                background.setImageResource(weatherWrapper.background)
                cityName.text = weatherWrapper.cityName
                temperature.text = weatherWrapper.temp
                mainIcon.setImageResource(weatherWrapper.mainIcon)
                cloudCover.text = weatherWrapper.description
                feelsLike.text = "${getString(R.string.feels_like)} ${weatherWrapper.fellsLike}"
                windSpeedText.text =
                    "${weatherWrapper.windSpeed} ${getString(R.string.m_s)} ${getString(weatherWrapper.windDeg.directionId)}"
                pressureText.text = "${weatherWrapper.pressure} ${getString(R.string.mmhg)}"
                humidityText.text = weatherWrapper.humidity

                adapterHourly.submitList(weatherWrapper.hourlyItems)
                adapterDaily.submitList(weatherWrapper.dailyItems)

                threebars.setOnClickListener {
                    requireActivity().openFragmentBase(
                        CityBaseFragment.newInstance(),
                        CityBaseFragment.TAG,
                        R.id.container
                    )
                }
            }

            lifecycleScope.launchWhenStarted {
                viewModel.loadingState.collectLatest { state ->
                    progress.isVisible = state
                }
            }
        }
    }

    private val onDailyItemClickListener by lazy {
        object : OnDailyItemClickListener {
            override fun onItemClick(item: DailyItem) {
                DailyBottomSheetFragment.newInstance(item)
                    .show(
                        requireActivity().supportFragmentManager,
                        DailyBottomSheetFragment.TAG
                    )
            }
        }
    }
}