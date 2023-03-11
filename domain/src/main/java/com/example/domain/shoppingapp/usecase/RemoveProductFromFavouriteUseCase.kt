package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.repository.UserRepository

class RemoveProductFromFavouriteUseCase(private val userRepository: UserRepository) {
    suspend fun execute(productName: String){
        val userId = userRepository.getCurrentUserId()
        userId?.let { userRepository.removeProductFromFavourite(it, productName) }
    }
}