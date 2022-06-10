package com.go0ose.openweather.presentation.fragments.search

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.go0ose.openweather.R
import com.go0ose.openweather.WeatherApplication
import com.go0ose.openweather.databinding.FragmentSearchBinding
import com.go0ose.openweather.domain.model.CityItem
import com.go0ose.openweather.presentation.fragments.search.recycler.OnSearchClickListener
import com.go0ose.openweather.presentation.fragments.search.recycler.SearchAdapter
import com.go0ose.openweather.presentation.fragments.weather.WeatherFragment
import com.go0ose.openweather.utils.ext.*
import javax.inject.Inject


class SearchFragment() : Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding()
    @Inject
    lateinit var viewModel: SearchViewModel
    private val adapterSearch by lazy { SearchAdapter(onItemClickListener) }
    private var backgroundId: Int = 0

    companion object {
        const val TAG = "SearchFragment"

        fun newInstance() = SearchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WeatherApplication.appComponent?.inject(this)

        binding.root.ifNetworkUnavailable{
            Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
        }
        backgroundId = viewModel.loadBackgroundFromPrefs()
        initViews()
        initObserver()
    }

    private fun initViews() {
        with(binding) {
            searchRecycler.adapter = adapterSearch
            background.setImageResource(backgroundId)
            back.setOnClickListener {
                it.startAnimation(
                    AnimationUtils.loadAnimation(context, R.anim.anim_image_button)
                )
                requireActivity().onBackPressed()
            }

            binding.searchCity.requestFocus()
            binding.root.showKeyboard()

            searchCity.setOnQueryListener { q ->
                viewModel.searchCity(q)
                return@setOnQueryListener false
            }
        }
    }

    private val onItemClickListener by lazy {
        object : OnSearchClickListener {
            override fun onItemClick(item: CityItem) {
                viewModel.openSearchWeather(item)
                binding.root.hideKeyboard()
            }
        }
    }

    private fun initObserver() {
        viewModel.cityItem.observe(viewLifecycleOwner) { listCityItem ->
            adapterSearch.submitList(listCityItem)
        }

        viewModel.cityWeather.observe(viewLifecycleOwner) { cityWeather ->
            requireActivity().openFragment(
                WeatherFragment.newInstance(cityWeather),
                WeatherFragment.TAG,
                R.id.container
            )
        }
    }
}
