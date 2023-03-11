package com.example.data.shoppingapp.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithFavProductsDbo(
    @Embedded
    val userDbo: UserDbo,
    @Relation(
        entity = FavouriteProductDbo::class,
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val favouriteList: List<FavouriteProductDbo>?
)
