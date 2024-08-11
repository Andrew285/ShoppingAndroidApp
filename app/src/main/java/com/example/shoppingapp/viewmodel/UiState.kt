package com.example.shoppingapp.viewmodel

import com.example.shoppingapp.model.CategoryModel
import com.example.shoppingapp.model.ProductModel

sealed class UiState {

    object Loading: UiState()

    data class Success(
        val productsList: ArrayList<ProductModel>
    ) : UiState()

    data class SuccessCategory(
        val categoriesList: ArrayList<CategoryModel>
    ) : UiState()
    data class Error(val message: String) : UiState()
}