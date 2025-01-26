package com.tayler.valushopping.repository.network.abstracts

import com.tayler.valushopping.repository.network.model.ImageResponse
import com.tayler.valushopping.repository.network.model.ProductResponse
import java.io.File

interface IDataNetwork {
    suspend fun saveProduct(data : ProductResponse):ProductResponse
    suspend fun loadProduct(all : Boolean,admin:Boolean): List<ProductResponse>
    suspend fun deleteProduct(idProduct : String): ProductResponse
    suspend fun updateProduct(product : ProductResponse): ProductResponse
    suspend fun saveImage(file: File?):ImageResponse
}