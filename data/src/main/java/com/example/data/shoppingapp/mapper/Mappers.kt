package com.example.data.shoppingapp.mapper

import com.example.data.shoppingapp.local.model.UserDbo
import com.example.data.shoppingapp.local.model.UserWithFavProductsDbo
import com.example.data.shoppingapp.remote.model.ProductDto
import com.example.data.shoppingapp.remote.model.ProductInfoDto
import com.example.domain.shoppingapp.model.NewUser
import com.example.domain.shoppingapp.model.Product
import com.example.domain.shoppingapp.model.ProductInfo
import com.example.domain.shoppingapp.model.User

fun ProductInfoDto.toDomain(): ProductInfo {
    return ProductInfo(
        this.colors,
        this.description,
        this.imageUrls,
        this.name,
        this.numberOfReviews,
        this.price,
        this.rating
    )
}

fun ProductDto.toDomain(): Product {
    return Product(this.category, this.discount, this.imageUrl, this.name, this.price)
}

fun UserDbo.toDomain(): User {
    return User(this.id!!, this.name, this.surname, this.photoUri, this.email, this.password)
}

fun NewUser.toDbo(): UserDbo {
    return UserDbo(null, this.email, this.name, this.surname, null, this.password)
}

