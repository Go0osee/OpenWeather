package com.go0ose.openweather.presentation.fragments.weather.recyclers

import com.go0ose.openweather.domain.model.DailyItem

interface OnDailyItemClickListener {

    fun onItemClick(item: DailyItem)
}