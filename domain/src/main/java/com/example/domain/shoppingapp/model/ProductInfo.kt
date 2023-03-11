package com.example.domain.shoppingapp.model

data class ProductInfo(
    val colors: List<String>,
    val description: String,
    val imageUrls: List<String>,
    val name: String,
    val numberOfReviews: Int,
    val price: Double,
    val rating: Double
)
