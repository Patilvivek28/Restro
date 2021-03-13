package com.si.restro.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.si.restro.R
import com.si.restro.data.Restaurants
import com.si.restro.databinding.FragmentDetailsBinding
import com.si.restro.ui.addorupdate.AddOrUpdateFragmentDirections
import com.si.restro.utils.value
import com.si.restro.viewmodel.RestaurantViewModel


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: RestaurantViewModel
    private var restaurantId = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            restaurantId = DetailsFragmentArgs.fromBundle(it).restaurantId
        }

        viewModel =
            ViewModelProvider(this).get(RestaurantViewModel::class.java)
        viewModel.fetch(restaurantId)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.currentRestaurant.observe(viewLifecycleOwner, Observer { restaurant ->
            restaurant?.let {
                binding.restaurant = restaurant

                binding.btnShowOnMap.setOnClickListener {

                    val gmmIntentUri =
                        Uri.parse("https://www.google.com/maps?q=" + restaurant.latitude + "," + restaurant.longitude)
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_nav_edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                val action = DetailsFragmentDirections.actionAddOrUpdateFragment()
                action.restaurantId = restaurantId
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}