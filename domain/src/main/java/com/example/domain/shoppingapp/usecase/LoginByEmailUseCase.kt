package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.model.LoginError
import com.example.domain.shoppingapp.model.UserLoginData
import com.example.domain.shoppingapp.repository.UserRepository

class LoginByEmailUseCase(private val userRepository: UserRepository) {
    suspend fun execute(userLoginData: UserLoginData): LoginError? {
        val user = userRepository.getUserByEmail(userLoginData.email)
        return if (user == null)
            LoginError.USER_NOT_FOUND
        else if (userLoginData.password != user.password)
            LoginError.WRONG_PASSWORD
        else {
            userRepository.setCurrentUserId(user.id)
            null
        }
    }
}