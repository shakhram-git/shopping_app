package com.example.domain.shoppingapp.model

data class User(
    val id: Long,
    val name: String,
    val surname: String,
    val photoUri: String?,
    val email: String,
    val password: String
)
