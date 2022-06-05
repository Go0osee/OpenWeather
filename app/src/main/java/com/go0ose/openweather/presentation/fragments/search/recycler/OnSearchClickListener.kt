package com.go0ose.openweather.presentation.fragments.search.recycler

import com.go0ose.openweather.domain.model.CityItem

interface OnSearchClickListener {
    fun onItemClick(item: CityItem)
}