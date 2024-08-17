package com.example.shoppingapp

import android.app.Application
import androidx.room.Room
import com.example.shoppingapp.database.ProductDatabase

class ShoppingApplication: Application() {

    companion object {
        @Volatile
        private lateinit var databaseInstance: ProductDatabase

        val database: ProductDatabase
            get() = databaseInstance
    }

    override fun onCreate() {
        super.onCreate()
        databaseInstance = Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java,
            "product_database"
        ).build()
    }
}