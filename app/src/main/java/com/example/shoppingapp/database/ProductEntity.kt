package com.example.shoppingapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppingapp.model.CategoryModel

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String?,
    val price: Float,
    val description: String?,
    val image: String,
    val creationAt: String?,
    val updatedAt: String?,
)