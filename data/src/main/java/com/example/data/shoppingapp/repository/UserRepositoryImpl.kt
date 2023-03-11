package com.example.data.shoppingapp.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.data.shoppingapp.local.model.FavouriteProductDbo
import com.example.data.shoppingapp.local.room.UserDao
import com.example.data.shoppingapp.mapper.toDbo
import com.example.data.shoppingapp.mapper.toDomain
import com.example.domain.shoppingapp.model.NewUser
import com.example.domain.shoppingapp.model.User
import com.example.domain.shoppingapp.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val prefsDataStore: DataStore<Preferences>
) : UserRepository {


    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.toDomain()
    }

    override fun getUserFlowById(userId: Long): Flow<User?> {
        return userDao.getUserFlowById(userId).map {
            it?.toDomain()
        }
    }
    override fun getUserFavouritesFlow(userId: Long): Flow<List<String>> {
        return userDao.getUserFavouritesFlow(userId)
    }

    override suspend fun addNewUser(newUser: NewUser): Long {
        return userDao.insertUser(newUser.toDbo())
    }

    override suspend fun addUserPhoto(userId: Long, photoUri: String) {
        userDao.addUserPhoto(userId, photoUri)
    }

    override suspend fun addProductToFavourite(userId: Long, productName: String) {
        userDao.addToFavourite(FavouriteProductDbo(userId = userId, name = productName))
    }
    override suspend fun removeProductFromFavourite(userId: Long, productName: String) {
        userDao.removeFromFavourite(userId, productName)
    }

    override suspend fun setCurrentUserId(id: Long) {
        prefsDataStore.edit { preferences ->
            preferences[CURRENT_USER_ID] = id
        }

    }

    override suspend fun removeCurrentUser() {
        prefsDataStore.edit { preferences ->
            preferences[CURRENT_USER_ID] = NO_CURRENT_USER_ID
        }
    }

    override suspend fun getCurrentUserId(): Long? {
        val result = prefsDataStore.data.first()[CURRENT_USER_ID]
        return if (result == NO_CURRENT_USER_ID) null else result
    }

    override fun getCurrentUserIdFlow(): Flow<Long?> {
        return prefsDataStore.data.map { pref ->
            val result = pref[CURRENT_USER_ID]
            return@map if (result == NO_CURRENT_USER_ID) null else result
        }
    }

    companion object {
        val CURRENT_USER_ID = longPreferencesKey("currentUserId")
        const val NO_CURRENT_USER_ID = -120L
    }
}