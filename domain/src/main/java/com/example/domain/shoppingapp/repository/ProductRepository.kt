package com.example.domain.shoppingapp.repository

import com.example.domain.shoppingapp.model.Product
import com.example.domain.shoppingapp.model.ProductInfo

interface ProductRepository {
    suspend fun getProductInfo(): ProductInfo
    suspend fun getLatestProducts(): List<Product>
    suspend fun getSaleProducts(): List<Product>
    suspend fun getProductsNames(): List<String>
}