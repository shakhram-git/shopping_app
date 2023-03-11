package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetUserFavouritesFlowUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): Flow<List<String>> {
        val userId = userRepository.getCurrentUserId()
        userId?.let {
            return userRepository.getUserFavouritesFlow(userId)
        } ?: return emptyFlow()
    }
}