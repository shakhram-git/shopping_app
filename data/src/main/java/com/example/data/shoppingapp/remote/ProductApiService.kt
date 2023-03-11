package com.example.data.shoppingapp.remote

import com.example.data.shoppingapp.remote.model.LatestProductsDto
import com.example.data.shoppingapp.remote.model.ProductInfoDto
import com.example.data.shoppingapp.remote.model.ProductsNamesDto
import com.example.data.shoppingapp.remote.model.SaleProductsDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ProductApiService {

    @GET("/v3/f7f99d04-4971-45d5-92e0-70333383c239")
    suspend fun getProductInfo(): Response<ProductInfoDto>

    @GET("/v3/cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
    suspend fun getLatestProducts(): Response<LatestProductsDto>

    @GET("/v3/a9ceeb6e-416d-4352-bde6-2203416576ac")
    suspend fun getSaleProducts(): Response<SaleProductsDto>

    @GET("/v3/4c9cd822-9479-4509-803d-63197e5a9e19")
    suspend fun getProductsNames(): Response<ProductsNamesDto>

    companion object{
        fun getInstance(): ProductApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create(ProductApiService::class.java)
        }
        private const val BASE_URL = "https://run.mocky.io"
    }
}