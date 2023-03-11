package com.example.domain.shoppingapp.usecase

import com.example.domain.shoppingapp.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetIsCurrentUserExistFlowUseCase(private val userRepository: UserRepository) {
    fun execute(): Flow<Boolean> {
        return userRepository.getCurrentUserIdFlow().map {
            return@map it != null
        }
    }
}