package com.si.restro.ui.addorupdate

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.si.restro.HomeScreenActivity
import com.si.restro.R
import com.si.restro.data.Restaurants
import com.si.restro.databinding.FragmentAddOrUpdateBinding
import com.si.restro.utils.value
import com.si.restro.viewmodel.RestaurantViewModel


class AddOrUpdateFragment : Fragment() {

    private lateinit var binding: FragmentAddOrUpdateBinding
    private lateinit var viewModel: RestaurantViewModel
    private var restaurantId = 0
    private var getLocationStarted = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        binding = FragmentAddOrUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            restaurantId = AddOrUpdateFragmentArgs.fromBundle(it).restaurantId
        }

        viewModel =
            ViewModelProvider(this).get(RestaurantViewModel::class.java)
        if (restaurantId != 0) {
            viewModel.fetch(restaurantId)
        }

        binding.btnGetLocation.setOnClickListener {
            getLocationStarted = true
            (activity as HomeScreenActivity).checkLocationPermission()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.currentRestaurant.observe(viewLifecycleOwner, Observer { restaurant ->
            restaurant?.let {
                binding.restaurant = restaurant
            }
        })
    }

    fun onPermissionResult(locLat: Double, locLong: Double) {
        binding.latitude.value = locLat.toString()
        binding.longitude.value = locLong.toString()
        Toast.makeText(context, "Location updated...!", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                if (isValidInput()) {
                    if (binding.restaurant != null) {
                        viewModel.updateRestaurant(
                            restaurantId,
                            binding.name.value,
                            binding.ratingBar.rating.toInt(),
                            binding.address.value,
                            binding.city.value,
                            binding.latitude.value.toDouble(),
                            binding.longitude.value.toDouble()
                        )

                        Toast.makeText(context, "Restaurant is updated...", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        viewModel.insertRestaurant(
                            Restaurants(
                                name = binding.name.value,
                                rating = binding.ratingBar.rating.toInt(),
                                address = binding.address.value,
                                city = binding.city.value,
                                latitude = binding.latitude.value.toDouble(),
                                longitude = binding.longitude.value.toDouble()
                            )
                        )

                        Toast.makeText(context, "New restaurant added...", Toast.LENGTH_SHORT)
                            .show()
                    }
                    val action = AddOrUpdateFragmentDirections.actionHomeFragment()
                    Navigation.findNavController(binding.root).navigate(action)
                } else {
                    Toast.makeText(context, "Enter all details...", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isValidInput(): Boolean {
        return binding.name.value.isNotEmpty()
                && binding.ratingBar.rating.isFinite()
                && binding.address.value.isNotEmpty()
                && binding.city.value.isNotEmpty()
                && binding.latitude.value.isNotEmpty()
                && binding.longitude.value.isNotEmpty()
    }


}