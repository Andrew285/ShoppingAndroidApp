package com.example.shoppingapp.retrofit

import retrofit2.http.GET

interface FakeStoreService {
    @GET("products")
    suspend fun getAllProducts(): ArrayList<ProductModel>
}