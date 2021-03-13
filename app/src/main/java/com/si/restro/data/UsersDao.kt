package com.si.restro.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersDao {

    @Insert
    suspend fun insert(user: Users)

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUser(email: String): Users
}