package com.go0ose.openweather.presentation.fragments.citybase

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.go0ose.openweather.R
import com.go0ose.openweather.WeatherApplication
import com.go0ose.openweather.databinding.FragmentCitybaseBinding
import com.go0ose.openweather.domain.model.CityWeatherFromDataBase
import com.go0ose.openweather.utils.ext.openFragment
import com.go0ose.openweather.presentation.fragments.citybase.recycler.CityBaseAdapter
import com.go0ose.openweather.presentation.fragments.citybase.recycler.OnCityBaseClickListener
import com.go0ose.openweather.presentation.fragments.citybase.recycler.SwipeToDeleteCallback
import com.go0ose.openweather.presentation.fragments.search.SearchFragment
import com.go0ose.openweather.presentation.fragments.weather.WeatherFragment
import com.go0ose.openweather.utils.ext.ifNetworkUnavailable
import javax.inject.Inject

class CityBaseFragment() : Fragment(R.layout.fragment_citybase) {

    private val binding: FragmentCitybaseBinding by viewBinding()
    @Inject
    lateinit var viewModel: CityBaseViewModel
    private val cityBaseAdapter by lazy { CityBaseAdapter(onItemClickListener) }
    private var backgroundId: Int = 0

    companion object {
        const val TAG = "CityBaseFragment"

        fun newInstance() = CityBaseFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WeatherApplication.appComponent?.inject(this)

        binding.root.ifNetworkUnavailable{
            Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
        }
        backgroundId =  viewModel.loadBackgroundFromPrefs()
        viewModel.loadCityWeatherFromDataBase()
        initViews()
        initObserver()
        viewModel.loadCityWeatherInternet()
    }

    private fun initViews() {
        binding.cityRecycler.adapter = cityBaseAdapter
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.cityRecycler)

        binding.background.setImageResource(backgroundId)

        binding.back.setOnClickListener {
            it.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.anim_image_button)
            )
            requireActivity().onBackPressed()
        }
        binding.search.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_image_button))
            requireActivity().openFragment(
                SearchFragment.newInstance(),
                SearchFragment.TAG,
                R.id.container
            )
        }
    }

    private fun initObserver() {
        viewModel.cityWeatherFromDataBase.observe(viewLifecycleOwner) { cityWeatherFromDataBase ->
            cityBaseAdapter.submitList(cityWeatherFromDataBase)
        }
    }

    private val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val item = viewModel.cityWeatherFromDataBase.value!![position]

            if (item.favorite != 1) {
                viewModel.deleteCity(item)
                binding.cityRecycler.adapter?.notifyItemRemoved(position)
                viewModel.deleteCityFromLiveData(position)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.it_is_not_possible_to_delete_the_main_city),
                    Toast.LENGTH_SHORT
                ).show()
                cityBaseAdapter.notifyDataSetChanged()
            }
        }
    }
    private val onItemClickListener by lazy {
        object : OnCityBaseClickListener {
            override fun onItemClick(item: CityWeatherFromDataBase) {
               requireActivity().openFragment(
                    WeatherFragment.newInstance(item),
                    WeatherFragment.TAG,
                    R.id.container
                )
            }
        }
    }
}