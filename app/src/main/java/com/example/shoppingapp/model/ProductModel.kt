package com.example.shoppingapp.model

data class ProductModel(
    val id: Int,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val image: String,
    val rating: ProductRating
)