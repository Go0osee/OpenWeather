package com.go0ose.openweather.presentation.fragments.weather.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.go0ose.openweather.databinding.ItemDailyWeatherBinding
import com.go0ose.openweather.domain.model.DailyItem

class DailyViewHolder(
    private val binding: ItemDailyWeatherBinding,
    private val onDailyItemClickListener: OnDailyItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun newInstance(
            parent: ViewGroup,
            onDailyItemClickListener: OnDailyItemClickListener
        ) = DailyViewHolder(
            ItemDailyWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onDailyItemClickListener
        )
    }

    fun bindItem(item: DailyItem) {
        with(itemView) {
            with(binding){
                dateText.text = item.date
                dayText.text = item.day
                weatherImage.setImageResource(item.iconDaily)
                nightTemp.text = item.tempNight
                temp.text = item.tempDay

                root.setOnClickListener {
                    onDailyItemClickListener.onItemClick(item)
                }
            }
        }

    }
}