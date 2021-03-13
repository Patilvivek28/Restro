package com.si.restro.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.si.restro.HomeScreenActivity
import com.si.restro.ui.entry.LoginActivity
import com.si.restro.R
import com.si.restro.data.Restaurants
import com.si.restro.utils.getJsonDataFromAsset
import com.si.restro.viewmodel.RestaurantViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var restaurantViewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel::class.java)

        val isFirstTime = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).getBoolean(
            "is_first_time",
            true
        )


        if (isFirstTime) {

            val jsonFileString =
                getJsonDataFromAsset(applicationContext, "default_restaurants.json")
            jsonFileString?.let { Log.i("data", it) }

            val gson = Gson()
            val listPersonType = object : TypeToken<List<Restaurants>>() {}.type

            var restaurants: List<Restaurants> = gson.fromJson(jsonFileString, listPersonType)
            restaurants.forEachIndexed { idx, restaurant ->
                Log.i("data", "> Item $idx:\n$restaurant")
                restaurantViewModel.insertRestaurant(restaurant)
            }
        }


        getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit().putBoolean(
            "is_first_time",
            false
        ).apply()


        val isLoggedIn = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).getBoolean(
            "is_logged_in",
            false
        )

        if (isLoggedIn) {
            startActivity(Intent(this, HomeScreenActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}