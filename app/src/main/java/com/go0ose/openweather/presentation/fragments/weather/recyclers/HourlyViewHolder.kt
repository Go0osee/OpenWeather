package com.go0ose.openweather.presentation.fragments.weather.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.go0ose.openweather.databinding.ItemHourWeatherBinding
import com.go0ose.openweather.domain.model.HourlyItem

class HourlyViewHolder(
    private val binding: ItemHourWeatherBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun newInstance(
            parent: ViewGroup,
        ) = HourlyViewHolder(
            ItemHourWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun bindItem(item: HourlyItem) {
        with(itemView) {
            with(binding) {
                timeText.text = item.time
                weatherImage.setImageResource(item.iconHourly)
                temperatureText.text = item.temp
            }
        }
    }
}