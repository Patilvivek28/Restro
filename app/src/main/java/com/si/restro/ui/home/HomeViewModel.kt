package com.si.restro.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.si.restro.BaseViewModel
import com.si.restro.data.Restaurants
import com.si.restro.data.RetroDatabase
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {

    val restaurants = MutableLiveData<List<Restaurants>>()
    val restaurantsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun fetchFromDatabase() {
        loading.value = true
        launch {
            val restaurants =
                RetroDatabase.getAppDatabase(getApplication()).restaurantsDao().getAllRestaurants()
            restaurantsRetrieved(restaurants)
        }
    }

    private fun restaurantsRetrieved(restaurantList: List<Restaurants>) {
        restaurants.value = restaurantList
        restaurantsLoadError.value = false
        loading.value = false
    }
}