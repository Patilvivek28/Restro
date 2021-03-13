package com.si.restro.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class Users(
    var name: String,
    @PrimaryKey
    var email: String,
    var passHash: String
)
