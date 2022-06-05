package com.go0ose.openweather.presentation.fragments.weather.recyclers

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.go0ose.openweather.R
import com.go0ose.openweather.domain.model.DailyItem

class DailyAdapter(
    private val onDailyItemClickListener: OnDailyItemClickListener
) : RecyclerView.Adapter<DailyViewHolder>() {

    private var items: List<DailyItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder.newInstance(parent, onDailyItemClickListener)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_item)
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(data: List<DailyItem>) {
        notifyDataSetChanged()
        items = data
    }
}