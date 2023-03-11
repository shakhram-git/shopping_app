package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.model.Product
import com.example.domain.shoppingapp.repository.ProductRepository

class GetLatestProductsUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(): List<Product> {
        return productRepository.getLatestProducts()
    }
}