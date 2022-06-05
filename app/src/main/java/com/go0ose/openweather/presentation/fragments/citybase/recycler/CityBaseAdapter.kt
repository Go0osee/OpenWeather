package com.go0ose.openweather.presentation.fragments.citybase.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase

class CityBaseAdapter(
    private val onCityBaseClickListener: OnCityBaseClickListener
) : RecyclerView.Adapter<CityBaseViewHolder>() {

    private var items: List<CityWeatherFromDataBase> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityBaseViewHolder {
        return CityBaseViewHolder.newInstance(parent, onCityBaseClickListener)
    }

    override fun onBindViewHolder(holder: CityBaseViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(data: List<CityWeatherFromDataBase>) {
        notifyDataSetChanged()
        items = data
    }
}