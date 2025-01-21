package com.tayler.valushopping.repository.network.api

import android.util.Log
import com.tayler.valushopping.repository.network.ServiceApi
import com.tayler.valushopping.repository.network.abstracts.IDataNetwork
import com.tayler.valushopping.repository.network.abstracts.base.BaseNetwork
import com.tayler.valushopping.repository.network.exception.GenericException
import com.tayler.valushopping.repository.network.model.ImageResponse
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.utils.toCompleteErrorModel
import com.tayler.valushopping.utils.validateBody
import com.tayler.valushopping.utils.validateData
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class DataNetwork @Inject constructor(private val serviceApi : ServiceApi,private val base : BaseNetwork):
    IDataNetwork {
    override suspend fun saveProduct(data : ProductResponse): ProductResponse {
        return base.executeWithConnection {
            var model  : ProductResponse?  = null
            val response  = serviceApi.saveProduct(data)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun loadProduct(): List<ProductResponse> {
        return base.executeWithConnection {
            var model  : List<ProductResponse>?  = null
            val response  = serviceApi.loadProduct()
            if (response.validateData()) {
                model = response.validateBody()
            }
            model?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun deleteProduct(idProduct : String): ProductResponse {
        return base.executeWithConnection {
            var model  : ProductResponse?  = null
            val response  = serviceApi.deleteProduct(idProduct)
            if (response.validateData()) {
                model = response.validateBody()
            }
            Log.d("servicess","deleteProductsuccpend")
            model?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun updateProduct(product : ProductResponse): ProductResponse {
        return base.executeWithConnection {
            var model  : ProductResponse?  = null
            val response  = serviceApi.updateProduct(product)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model?: throw response.errorBody().toCompleteErrorModel(response.code())

        }    }

    override suspend fun saveImage(file: File?): ImageResponse {
        return base.executeWithConnection {
                 file?.let {
                     val image = it.asRequestBody("image/*".toMediaType())
                     val multiPartBody = MultipartBody.Part.createFormData("archivo",it.name,image)
                     val response = serviceApi.saveImage(multiPartBody)
                     var model : ImageResponse? = null
                     if (response.isSuccessful && response.body() != null) {
                         model = response.validateBody()
                     }
                     model?: throw response.errorBody().toCompleteErrorModel(response.code())
                 }?: throw GenericException()

        }
    }
}