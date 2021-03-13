package com.si.restro.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.si.restro.BaseViewModel
import com.si.restro.data.Restaurants
import com.si.restro.data.RetroDatabase
import com.si.restro.data.RetroRepository
import kotlinx.coroutines.launch

class RestaurantViewModel(application: Application) : BaseViewModel(application) {


    val currentRestaurant = MutableLiveData<Restaurants>()
    private val retroDatabase = RetroDatabase.getAppDatabase(application);
    private val retroRepository =
        RetroRepository.getInstance(retroDatabase.userDao(), retroDatabase.restaurantsDao())

    internal fun insertRestaurant(restaurant: Restaurants) {
        launch {
            retroRepository.insertRestaurant(restaurant)
        }
    }

    internal fun updateRestaurant(
        id: Int,
        name: String,
        rating: Int,
        address: String,
        city: String,
        latitude: Double,
        longitude: Double
    ) {
        launch {
            retroRepository.updateRestaurant(id, name, rating, address, city, latitude, longitude)
        }
    }

    fun fetch(restaurantId: Int) {
        launch {
            val restaurant = retroRepository.getRestaurant(restaurantId)
            currentRestaurant.value = restaurant
        }
    }

}