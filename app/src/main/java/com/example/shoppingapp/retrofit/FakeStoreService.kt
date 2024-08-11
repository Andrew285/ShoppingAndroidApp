package com.example.shoppingapp.retrofit

import com.example.shoppingapp.model.CategoryModel
import com.example.shoppingapp.model.ProductModel
import retrofit2.http.GET

interface FakeStoreService {
    @GET("products/")
    suspend fun getAllProducts(): ArrayList<ProductModel>

    @GET("categories/")
    suspend fun getAllCategories(): ArrayList<CategoryModel>
}