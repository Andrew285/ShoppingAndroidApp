package com.example.shoppingapp.database

import android.util.Log
import com.example.shoppingapp.ShoppingApplication
import com.example.shoppingapp.model.ProductModel

class ProductRepository(private val database: ProductDatabase) {
    private val productDao = database.productDao()

    suspend fun insertToCartDB(product: ProductModel) {
        val entity = convertToEntity(product)
        productDao.insertProduct(entity)
    }

    suspend fun getProductsFromCartDB(): List<ProductModel> {
        val entities = productDao.getAllProducts()
        return entities.map {
            convertToModel(it)
        }
    }

    private fun convertToModel(entity: ProductEntity): ProductModel {
        return ProductModel(
            id = entity.id,
            title = entity.title,
            price = entity.price,
            description = entity.description,
            images = arrayListOf(entity.image),
            creationAt = entity.creationAt,
            updatedAt = entity.updatedAt,
            category = null
            // Add other fields as needed
        )
    }

    private fun convertToEntity(model: ProductModel): ProductEntity {
        return ProductEntity(
            id = model.id,
            title = model.title,
            price = model.price,
            description = model.description,
            image = model.images[0],
            creationAt = model.creationAt,
            updatedAt = model.updatedAt
        )
    }
}