package com.example.domain.shoppingapp.repository

import com.example.domain.shoppingapp.model.NewUser
import com.example.domain.shoppingapp.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUserByEmail(email: String): User?
    suspend fun addNewUser(newUser: NewUser): Long
    suspend fun setCurrentUserId(id: Long)
    suspend fun getCurrentUserId(): Long?
    suspend fun removeCurrentUser()
    fun getUserFlowById(userId: Long): Flow<User?>
    fun getCurrentUserIdFlow(): Flow<Long?>
    suspend fun addUserPhoto(userId: Long, photoUri: String)
    suspend fun addProductToFavourite(userId: Long, productName: String)
    suspend fun removeProductFromFavourite(userId: Long, productName: String)
    fun getUserFavouritesFlow(userId: Long): Flow<List<String>>
}