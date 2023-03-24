package com.go0ose.openweather.presentation.fragments.search.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.go0ose.openweather.databinding.ItemSearchBinding
import com.go0ose.openweather.domain.model.CityItem

class SearchViewHolder(
    private val binding: ItemSearchBinding,
    private val onSearchClickListener: OnSearchClickListener
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun newInstance(
            parent: ViewGroup,
            onSearchClickListener: OnSearchClickListener
        ) = SearchViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onSearchClickListener
        )
    }

    fun bindItem(item: CityItem) {
        with(itemView) {
            with(binding) {
                cityName.text = item.fullName
                root.setOnClickListener {
                    onSearchClickListener.onItemClick(item)
                }
            }
        }
    }
}