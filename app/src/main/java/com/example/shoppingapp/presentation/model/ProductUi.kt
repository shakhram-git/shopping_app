package com.example.shoppingapp.presentation.model

data class ProductUi(
    val category: String,
    val discount: Int?,
    val imageUrl: String,
    val name: String,
    val price: Double
): ListItem