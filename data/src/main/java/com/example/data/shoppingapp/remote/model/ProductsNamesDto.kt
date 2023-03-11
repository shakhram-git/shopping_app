package com.example.data.shoppingapp.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductsNamesDto(
    @Json(name = "words")
    val names: List<String>
)