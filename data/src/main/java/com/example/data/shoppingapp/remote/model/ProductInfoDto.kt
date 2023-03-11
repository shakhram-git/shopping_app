package com.example.data.shoppingapp.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductInfoDto(
    val colors: List<String>,
    val description: String,
    @Json(name = "image_urls")
    val imageUrls: List<String>,
    val name: String,
    @Json(name = "number_of_reviews")
    val numberOfReviews: Int,
    val price: Double,
    val rating: Double
)