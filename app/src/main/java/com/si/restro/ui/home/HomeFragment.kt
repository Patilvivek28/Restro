package com.si.restro.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.si.restro.R
import com.si.restro.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private val restaurantsListAdapter = RestaurantsListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.fetchFromDatabase()

        binding.restaurantList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = restaurantsListAdapter
        }

        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionAddOrUpdateFragment()
            Navigation.findNavController(view).navigate(action)
        }

        observeViewModel()
    }


    private fun observeViewModel() {
        homeViewModel.restaurants.observe(viewLifecycleOwner, Observer { restaurants ->
            restaurants?.let {
                binding.restaurantList.visibility = View.VISIBLE
                restaurantsListAdapter.updateRestaurantList(restaurants)
            }
        })

        homeViewModel.restaurantsLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                binding.listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        homeViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.restaurantList.visibility = View.GONE
                }
            }
        })
    }
}