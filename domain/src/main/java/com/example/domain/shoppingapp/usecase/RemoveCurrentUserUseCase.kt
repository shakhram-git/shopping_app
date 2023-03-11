package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.repository.UserRepository

class RemoveCurrentUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(){
        userRepository.removeCurrentUser()
    }
}