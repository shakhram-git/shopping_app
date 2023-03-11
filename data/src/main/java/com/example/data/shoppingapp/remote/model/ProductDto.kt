package com.example.data.shoppingapp.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDto(
    val category: String,
    val discount: Int?,
    @Json(name = "image_url")
    val imageUrl: String,
    val name: String,
    val price: Double
)