package com.example.shoppingapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.data.shoppingapp.local.room.UserDao
import com.example.data.shoppingapp.remote.ProductApiService
import com.example.data.shoppingapp.repository.ProductRepositoryImpl
import com.example.data.shoppingapp.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.util.prefs.Preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideProductApiService(): ProductApiService {
        return ProductApiService.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserDao(@ApplicationContext context: Context): UserDao {
        return UserDao.getInstance(context)
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<androidx.datastore.preferences.core.Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Singleton
    @Provides
    fun provideUserRepositoryImpl(
        userDao: UserDao,
        prefsDataStore: DataStore<androidx.datastore.preferences.core.Preferences>
    ): UserRepositoryImpl {
        return UserRepositoryImpl(userDao, prefsDataStore)
    }

    @Singleton
    @Provides
    fun provideProductRepositoryImpl(
        productApiService: ProductApiService
    ): ProductRepositoryImpl {
        return ProductRepositoryImpl(productApiService)
    }

    companion object {
        const val USER_PREFERENCES = "user_preferences"
    }
}