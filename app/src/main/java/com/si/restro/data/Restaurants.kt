package com.si.restro.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "restaurants")
data class Restaurants(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var rating: Int,
    var image: String = "https://images.unsplash.com/photo-1544461772-722f499fa2a9?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=1050&q=80",
    var address: String,
    var city: String,
    @SerializedName("loc_lat")
    var latitude: Double,
    @SerializedName("loc_long")
    var longitude: Double
)