package com.go0ose.openweather.presentation.fragments.search.recycler

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.go0ose.openweather.R
import com.go0ose.openweather.domain.model.CityItem

class SearchAdapter(
    private val onSearchClickListener: OnSearchClickListener
) : RecyclerView.Adapter<SearchViewHolder>() {

    private var items: List<CityItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.newInstance(parent, onSearchClickListener)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_item)
        holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(data: List<CityItem>) {
        notifyDataSetChanged()
        items = data
    }
}