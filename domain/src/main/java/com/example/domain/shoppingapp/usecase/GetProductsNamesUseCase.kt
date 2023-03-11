package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.repository.ProductRepository

class GetProductsNamesUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(): List<String> {
        return productRepository.getProductsNames()
    }
}