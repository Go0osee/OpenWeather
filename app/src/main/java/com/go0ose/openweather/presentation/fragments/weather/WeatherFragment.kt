package com.go0ose.openweather.presentation.fragments.weather

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.go0ose.openweather.R
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.go0ose.openweather.WeatherApplication
import com.go0ose.openweather.databinding.FragmentWeatherBinding
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.domain.model.DailyItem
import com.go0ose.openweather.utils.ext.openFragmentBase
import com.go0ose.openweather.presentation.fragments.citybase.CityBaseFragment
import com.go0ose.openweather.presentation.fragments.daily.DailyBottomSheetFragment
import com.go0ose.openweather.presentation.fragments.weather.recyclers.DailyAdapter
import com.go0ose.openweather.presentation.fragments.weather.recyclers.HourlyAdapter
import com.go0ose.openweather.presentation.fragments.weather.recyclers.OnDailyItemClickListener
import javax.inject.Inject

class WeatherFragment() : Fragment(R.layout.fragment_weather) {

    companion object {
        const val TAG = "WeatherFragment"
        private const val WEATHER_KEY = "weather_key"
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

    private val cityWeatherFromDataBase: CityWeatherFromDataBase by lazy { arguments?.getParcelable(WEATHER_KEY)!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WeatherApplication.appComponent?.inject(this)

        viewModel.loadWeather(cityWeatherFromDataBase)
        initViews()
        initObserver()
    }

    private fun initViews() {
        with(binding) {
            when (cityWeatherFromDataBase.favorite) {
                2 -> {
                    addOrFavorite.setImageResource(R.drawable.ic_main_favourite)
                    addOrFavorite.setOnClickListener {
                        it.startAnimation(
                            AnimationUtils.loadAnimation(context, R.anim.anim_image_button)
                        )
                        cityWeatherFromDataBase.favorite = 1

                        viewModel.changeFavorite(cityWeatherFromDataBase)
                        Toast.makeText(requireContext(), "Теперь это основной город", Toast.LENGTH_SHORT).show()

                        addOrFavorite.setImageResource(0)
                        initViews()
                    }
                }
                3 -> {
                    addOrFavorite.setImageResource(R.drawable.ic_main_plus)
                    addOrFavorite.setOnClickListener {
                        it.startAnimation(
                            AnimationUtils.loadAnimation(context, R.anim.anim_image_button)
                        )
                        cityWeatherFromDataBase.favorite = 2
                        viewModel.addToDataBase(cityWeatherFromDataBase)
                        Toast.makeText(requireContext(), "Добавлено", Toast.LENGTH_SHORT).show()
                        initViews()
                    }
                }
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
                feelsLike.text = weatherWrapper.fellsLike
                windSpeedText.text = weatherWrapper.windSpeed
                pressureText.text = weatherWrapper.pressure
                humidityText.text = weatherWrapper.humidity

                adapterHourly.submitList(weatherWrapper.hourlyItems)
                adapterDaily.submitList(weatherWrapper.dailyItems)

                threebars.setOnClickListener {
                    it.startAnimation(
                        AnimationUtils.loadAnimation(context, R.anim.anim_image_button)
                    )

                    requireActivity().openFragmentBase(
                        CityBaseFragment.newInstance(),
                        CityBaseFragment.TAG,
                        R.id.container
                    )
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