package com.example.domain.shoppingapp.model

data class Product(
    val category: String,
    val discount: Int?,
    val imageUrl: String,
    val name: String,
    val price: Double
)