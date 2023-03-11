package com.example.data.shoppingapp.repository

import com.example.data.shoppingapp.mapper.toDomain
import com.example.data.shoppingapp.remote.ProductApiService
import com.example.domain.shoppingapp.model.Product
import com.example.domain.shoppingapp.model.ProductInfo
import com.example.domain.shoppingapp.repository.ProductRepository
import retrofit2.HttpException

class ProductRepositoryImpl constructor(private val productApiService: ProductApiService) :
    ProductRepository {
    override suspend fun getProductInfo(): ProductInfo {
        val response = productApiService.getProductInfo()
        if (response.isSuccessful)
            return response.body()?.toDomain() ?: throw HttpException(response)
        else throw HttpException(response)
    }

    override suspend fun getLatestProducts(): List<Product> {
        val response = productApiService.getLatestProducts()
        if (response.isSuccessful)
            return response.body()?.products?.map { it.toDomain() } ?: throw HttpException(response)
        else throw HttpException(response)
    }

    override suspend fun getSaleProducts(): List<Product> {
        val response = productApiService.getSaleProducts()
        if (response.isSuccessful)
            return response.body()?.products?.map { it.toDomain() } ?: throw HttpException(response)
        else throw HttpException(response)
    }

    override suspend fun getProductsNames(): List<String> {
        val response = productApiService.getProductsNames()
        if (response.isSuccessful)
            return response.body()?.names ?: throw HttpException(response)
        else throw HttpException(response)
    }
}