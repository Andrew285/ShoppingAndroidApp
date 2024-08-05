package com.example.shoppingapp.viewmodel

import com.example.shoppingapp.retrofit.ProductModel

sealed class UiState {

    object Loading: UiState()

    data class Success(
        val productsList: ArrayList<ProductModel>
    ) : UiState()

    data class Error(val message: String) : UiState()
}