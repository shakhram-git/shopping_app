package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.model.NewUser
import com.example.domain.shoppingapp.repository.UserRepository


class CreateNewUserUseCase(private val userRepository: UserRepository) {

    suspend fun execute(newUser: NewUser){
        val id = userRepository.addNewUser(newUser)
        userRepository.setCurrentUserId(id)
    }
}