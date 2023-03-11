package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.model.ProductInfo
import com.example.domain.shoppingapp.repository.ProductRepository

class GetProductInfoUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(): ProductInfo {
        return productRepository.getProductInfo()
    }
}