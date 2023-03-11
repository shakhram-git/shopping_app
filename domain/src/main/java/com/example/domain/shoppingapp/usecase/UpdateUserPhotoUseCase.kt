package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.repository.UserRepository

class UpdateUserPhotoUseCase(private val userRepository: UserRepository) {
    suspend fun execute(userId: Long, photoUri: String){
        userRepository.addUserPhoto(userId, photoUri)
    }
}