package com.si.restro.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class, Restaurants::class], version = 1)
abstract class RetroDatabase : RoomDatabase() {

    abstract fun userDao(): UsersDao
    abstract fun restaurantsDao(): RestaurantsDao

    companion object {
        private var INSTANCE: RetroDatabase? = null

        fun getAppDatabase(context: Context): RetroDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RetroDatabase::class.java,
                    "retro-db"
                )
                    .build();
            }

            return INSTANCE!!
        }
    }
}