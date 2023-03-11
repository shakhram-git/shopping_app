package com.example.shoppingapp.di

import com.example.domain.shoppingapp.repository.ProductRepository
import com.example.domain.shoppingapp.repository.UserRepository
import com.example.domain.shoppingapp.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    @ViewModelScoped
    fun provideCreateNewUserUseCase(userRepository: UserRepository): CreateNewUserUseCase {
        return CreateNewUserUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCurrentUserInfoFlowUseCase(userRepository: UserRepository): GetCurrentUserInfoFlowUseCase {
        return GetCurrentUserInfoFlowUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetIsCurrentUserExistFlowUseCase(userRepository: UserRepository): GetIsCurrentUserExistFlowUseCase {
        return GetIsCurrentUserExistFlowUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetLatestProductsUseCase(productRepository: ProductRepository): GetLatestProductsUseCase {
        return GetLatestProductsUseCase(productRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetProductInfoUseCase(productRepository: ProductRepository): GetProductInfoUseCase {
        return GetProductInfoUseCase(productRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetProductsNamesUseCase(productRepository: ProductRepository): GetProductsNamesUseCase {
        return GetProductsNamesUseCase(productRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetSaleProductsUseCase(productRepository: ProductRepository): GetSaleProductsUseCase {
        return GetSaleProductsUseCase(productRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLoginByEmailUseCase(userRepository: UserRepository): LoginByEmailUseCase {
        return LoginByEmailUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRemoveCurrentUserUseCase(userRepository: UserRepository): RemoveCurrentUserUseCase {
        return RemoveCurrentUserUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateUserPhotoUseCase(userRepository: UserRepository): UpdateUserPhotoUseCase {
        return UpdateUserPhotoUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAddProductToFavouriteUseCase(userRepository: UserRepository): AddProductToFavouriteUseCase {
        return AddProductToFavouriteUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRemoveProductFromFavouriteUseCase(userRepository: UserRepository): RemoveProductFromFavouriteUseCase {
        return RemoveProductFromFavouriteUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUserFavouritesFlowUseCase(userRepository: UserRepository): GetUserFavouritesFlowUseCase {
        return GetUserFavouritesFlowUseCase(userRepository)
    }


}