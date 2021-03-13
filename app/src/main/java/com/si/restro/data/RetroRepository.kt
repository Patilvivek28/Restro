package com.si.restro.data

class RetroRepository private constructor(
    private val usersDao: UsersDao,
    private val restaurantsDao: RestaurantsDao
) {

    suspend fun isValidAccount(email: String, password: String): Boolean {
        val user = usersDao.getUser(email) ?: return false
        return user.passHash == password
    }

    suspend fun getUser(email: String): Users {
        return usersDao.getUser(email)
    }

    suspend fun insertUser(name: String, email: String, password: String) {
        val user = Users(name, email, password)
        usersDao.insert(user)
    }

    suspend fun insertRestaurant(restaurant: Restaurants) {
        restaurantsDao.insert(restaurant)
    }

    suspend fun updateRestaurant(
        id: Int, name: String,
        rating: Int,
        address: String,
        city: String,
        latitude: Double,
        longitude: Double
    ) {
        restaurantsDao.update(id, name, rating, address, city, latitude, longitude)
    }

    suspend fun getRestaurant(restaurantId: Int): Restaurants {
        return restaurantsDao.getRestaurant(restaurantId)
    }


    companion object {
        private var INSTANCE: RetroRepository? = null

        fun getInstance(usersDao: UsersDao, restaurantsDao: RestaurantsDao): RetroRepository {
            if (INSTANCE == null) {
                INSTANCE = RetroRepository(usersDao, restaurantsDao)
            }
            return INSTANCE!!
        }
    }
}