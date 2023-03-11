package com.example.data.shoppingapp.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SaleProductsDto(
    @Json(name = "flash_sale")
    val products: List<ProductDto>
)