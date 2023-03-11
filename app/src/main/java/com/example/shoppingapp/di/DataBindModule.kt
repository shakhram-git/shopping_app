package com.example.shoppingapp.di

import com.example.data.shoppingapp.repository.ProductRepositoryImpl
import com.example.data.shoppingapp.repository.UserRepositoryImpl
import com.example.domain.shoppingapp.repository.ProductRepository
import com.example.domain.shoppingapp.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {
    @Singleton
    @Binds
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}
