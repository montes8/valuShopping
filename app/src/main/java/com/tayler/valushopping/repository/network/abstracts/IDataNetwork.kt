package com.tayler.valushopping.repository.network.abstracts

import com.tayler.valushopping.repository.network.model.ImageResponse
import com.tayler.valushopping.repository.network.model.ProductResponse
import java.io.File

interface IDataNetwork {
    suspend fun saveProduct(data : ProductResponse):ProductResponse
    suspend fun loadProduct(): List<ProductResponse>
    suspend fun saveImage(file: File?):ImageResponse
}