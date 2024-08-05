package com.example.shoppingapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.retrofit.FakeStoreService
import com.example.shoppingapp.retrofit.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel: BaseViewModel<UiState>() {

    fun loadProductData() {
        uiState.value = UiState.Loading
        var recommendedProductsList: ArrayList<ProductModel> = ArrayList()
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).async {
                try {
                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://fakestoreapi.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val service = retrofit.create(FakeStoreService::class.java)
                    recommendedProductsList = service.getAllProducts()
                } catch (e: Exception) {
                    uiState.value = UiState.Error("Network request failed")
                }
            }.await()

            if (recommendedProductsList.isNotEmpty()) {
                uiState.value = UiState.Success(recommendedProductsList)
            }
        }
    }
}