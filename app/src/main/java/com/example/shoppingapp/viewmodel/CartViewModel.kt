package com.example.shoppingapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.ShoppingApplication
import com.example.shoppingapp.database.DatabaseProvider
import com.example.shoppingapp.database.ProductRepository
import com.example.shoppingapp.model.ProductModel
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private var productRepository: ProductRepository = ProductRepository(ShoppingApplication.database)

    // MutableLiveData to hold the cart items
    val _cartItems = MutableLiveData<List<ProductModel>>()
    val cartProducts: LiveData<List<ProductModel>> get() = _cartItems

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            _cartItems.value = productRepository.getProductsFromCartDB()
            Log.d("Test", _cartItems.value.toString())
        }
    }

    fun addToCart(product: ProductModel) {
        viewModelScope.launch {
            productRepository.insertToCartDB(product)
            loadCartItems()
        }
    }

}