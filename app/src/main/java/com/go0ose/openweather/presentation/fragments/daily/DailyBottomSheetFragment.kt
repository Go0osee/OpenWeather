package com.go0ose.openweather.presentation.fragments.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.go0ose.openweather.R
import com.go0ose.openweather.databinding.FragmentDailyBottomSheetBinding
import com.go0ose.openweather.domain.model.DailyItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DailyBottomSheetFragment() : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "DailyBottomSheetFragment"
        private const val DAILY_WEATHER_KEY = "daily_weather_key"
        fun newInstance(dailyItem: DailyItem) = DailyBottomSheetFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelable(
                        DailyBottomSheetFragment.DAILY_WEATHER_KEY,
                        dailyItem
                    )
                }
            }
    }

    private val binding: FragmentDailyBottomSheetBinding by viewBinding()
    private val dailyItem: DailyItem by lazy { arguments?.getParcelable(DAILY_WEATHER_KEY)!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_daily_bottom_sheet, container, false)

    override fun onStart() {
        super.onStart()

        initViews()
    }

    private fun initViews() {
        with(binding) {
            dayText.text = dailyItem.day
            dateText.text = dailyItem.date
            weatherImage.setImageResource(dailyItem.iconDaily)
            windSpeedText.text =
                "${dailyItem.windSpeed} ${getString(R.string.m_s)} ${getString(dailyItem.windDeg.directionId)}"
            pressureText.text = "${dailyItem.pressure} ${getString(R.string.mmhg)}"
            humidityText.text = dailyItem.humidity
            tempMorn.text = dailyItem.tempMorn
            tempDay.text = dailyItem.tempDay
            tempEve.text = dailyItem.tempEve
            tempNight.text = dailyItem.tempNight
            feelsLikeMorn.text = dailyItem.feelsLikeMorn
            feelsLikeDay.text = dailyItem.feelsLikeDay
            feelsLikeEve.text = dailyItem.feelsLikeEve
            feelsLikeNight.text = dailyItem.feelsLikeNight
            sunriseText.text = dailyItem.sunrise
            sunsetText.text = dailyItem.sunset
            moonImage.setImageResource(dailyItem.moonPhase.icon)
            moonText.setText(dailyItem.moonPhase.description)
            uvText.text = "${dailyItem.uvi.index}, ${getString(dailyItem.uvi.description)}"
            rainText.text = dailyItem.pop
        }
    }
}