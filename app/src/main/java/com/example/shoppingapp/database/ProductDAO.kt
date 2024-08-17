package com.example.shoppingapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDAO {
    @Query("SELECT * FROM products")
    suspend fun getAllProducts() : List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(entity: ProductEntity)
}