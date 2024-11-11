package com.example.shoppingapp
import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.shoppingapp.database.ProductDatabase
import com.example.shoppingapp.model.ProductModel
import com.example.shoppingapp.viewmodel.CartViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TestCartViewModel {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var cartViewModel: CartViewModel
    private lateinit var testDatabase: ProductDatabase

    @Before
    fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        testDatabase = Room.inMemoryDatabaseBuilder(
            application.applicationContext,
            ProductDatabase::class.java
        ).allowMainThreadQueries().build()

        // Set the test database instance to the companion object
        ShoppingApplication.databaseInstance = testDatabase
        cartViewModel = CartViewModel(application)
    }

    @Test
    fun `loadCartItems should update cartProducts LiveData`() = runTest {
        val initialProducts = listOf(
            ProductModel(1, "Product 1", 10.0f, "Description 1", arrayListOf("image url 1"), null, null, null),
            ProductModel(2, "Product 2", 20.0f, "Description 2", arrayListOf("image url 2"), null, null, null),
        )
        cartViewModel._cartItems.value = initialProducts
        cartViewModel.cartProducts.observeForever { observedProducts ->
            assertEquals(initialProducts, observedProducts)
        }
    }

    @Test
    fun `calculatePrices should correctly update subTotalPrice`() {
        val product1 = ProductModel(1, "Product 1", 10.0f, "Description 1", arrayListOf("image url 1"), null, null, null)
        val product2 = ProductModel(2, "Product 2", 20.0f, "Description 2", arrayListOf("image url 2"), null, null, null)
        cartViewModel._cartItems.value = listOf(product1, product2)

        cartViewModel.calculatePrices()
        cartViewModel.subTotalPrice.value?.let { assertEquals(30.0, it, 0.001) }
    }

    @Test
    fun `addToCart should add a product and update cartItems`() = runTest {
        val product = ProductModel(1, "Product 1", 10.0f, "Description 1", arrayListOf("image url 1"), null, null, null)
        cartViewModel.addToCart(product)
        cartViewModel.cartProducts.observeForever {
            assert(it.any { item -> item.id == product.id })
        }
    }

    @Test
    fun `calculateTotalPrice should set totalPrice correctly`() {
        cartViewModel.subTotalPrice.value = 50.0
        cartViewModel.deliveryPrice.value = 20.0

        cartViewModel.calculateTotalPrice()
        cartViewModel.totalPrice.value?.let { assertEquals(70.0, it, 0.001) }
    }
}