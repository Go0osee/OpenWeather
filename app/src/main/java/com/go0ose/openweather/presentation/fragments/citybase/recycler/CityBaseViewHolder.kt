package com.go0ose.openweather.presentation.fragments.citybase.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.go0ose.openweather.R
import com.go0ose.openweather.databinding.ItemCityBinding
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase

class CityBaseViewHolder(
    private val binding: ItemCityBinding,
    private val onCityBaseClickListener: OnCityBaseClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun newInstance(
            parent: ViewGroup,
            onCityBaseClickListener: OnCityBaseClickListener
        ) = CityBaseViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onCityBaseClickListener
        )
    }

    fun bindItem(item: CityWeatherFromDataBase) {
        with(itemView) {
            with(binding) {
                if(item.favorite == 1) {
                    favoriteImage.setImageResource(R.drawable.ic_main_star)
                }
                cityName.text = item.cityName
                description.text = item.description
                weatherImage.setImageResource(item.mainIcon)
                temp.text = item.temp
                feelsLike.text = item.fellsLike

                root.setOnClickListener {
                    itemView.startAnimation(
                        AnimationUtils.loadAnimation(context, R.anim.anim_image_button)
                    )
                    onCityBaseClickListener.onItemClick(item)
                }
            }
        }
    }
}