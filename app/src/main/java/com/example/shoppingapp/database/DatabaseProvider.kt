package com.example.shoppingapp.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var databaseInstance: ProductDatabase? = null

    fun getDatabase(context: Context): ProductDatabase {
        return databaseInstance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ProductDatabase::class.java,
                "product_database"
            ).build()
            databaseInstance = instance
            instance
        }
    }
}