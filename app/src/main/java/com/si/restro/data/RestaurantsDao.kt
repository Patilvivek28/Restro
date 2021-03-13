package com.si.restro.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RestaurantsDao {

    @Insert
    suspend fun insert(restaurant: Restaurants)

    @Query("UPDATE restaurants SET name = :name, rating = :rating, address=:address, city=:city, latitude=:latitude, longitude=:longitude WHERE id = :id")
    suspend fun update(
        id: Int,
        name: String,
        rating: Int,
        address: String,
        city: String,
        latitude: Double,
        longitude: Double
    )

    @Query("SELECT * FROM restaurants WHERE id = :id")
    suspend fun getRestaurant(id: Int): Restaurants

    @Query("SELECT * FROM restaurants")
    suspend fun getAllRestaurants(): List<Restaurants>
}