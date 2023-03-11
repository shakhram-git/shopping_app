package com.example.data.shoppingapp.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index("email", unique = true)]
)
data class UserDbo(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val email: String,
    val name: String,
    val surname: String,
    val photoUri: String?,
    val password: String
)
