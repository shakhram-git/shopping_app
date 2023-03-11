package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.exception.InvalidUserIdException
import com.example.domain.shoppingapp.model.User
import com.example.domain.shoppingapp.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserInfoFlowUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): Flow<User?> {
        val userId = userRepository.getCurrentUserId()
        userId?.let {
            return userRepository.getUserFlowById(userId)
        } ?: throw InvalidUserIdException()
    }
}