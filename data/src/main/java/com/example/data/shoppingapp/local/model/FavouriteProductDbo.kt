package com.example.data.shoppingapp.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "fav_products"
)
data class FavouriteProductDbo(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(name = "user_id")
    val userId: Long,
    val name: String
)
