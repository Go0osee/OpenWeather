package com.go0ose.openweather.presentation.fragments.weather.recyclers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.go0ose.openweather.domain.model.HourlyItem

class HourlyAdapter: RecyclerView.Adapter<HourlyViewHolder>() {

    private var items: List<HourlyItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        return HourlyViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(data: List<HourlyItem>) {
        notifyDataSetChanged()
        items = data
    }
}