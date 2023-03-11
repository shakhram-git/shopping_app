package com.example.shoppingapp.presentation.mapper

import com.example.domain.shoppingapp.model.Product
import com.example.shoppingapp.presentation.model.ProductUi

fun Product.toUi(): ProductUi {
    return ProductUi(this.category, this.discount, this.imageUrl, this.name, this.price)
}