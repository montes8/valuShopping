package com.tayler.valushopping.repository.network

import com.tayler.valushopping.repository.network.model.ImageResponse
import com.tayler.valushopping.repository.network.model.LoginResponse
import com.tayler.valushopping.repository.network.model.ParamResponse
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.repository.network.model.request.LoginRequest
import com.tayler.valushopping.repository.network.model.response.ImageMoreResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ServiceApi {

    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @GET("config/param")
    suspend fun loadParam(): Response<ParamResponse>?

    @POST("config/param")
    suspend fun saveParam(@Body paramResponse: ParamResponse): Response<ParamResponse>

    @PUT("config/param/{id}")
    suspend fun updateParam(@Path("id")id : String,
                            @Body paramResponse: ParamResponse): Response<ParamResponse>

    @Multipart
    @POST("uploads/product")
    suspend fun saveImage(
        @Part file : MultipartBody.Part): Response<ImageResponse>

    @POST("product")
    suspend fun saveProduct(@Body productResponse: ProductResponse): Response<ProductResponse>

    @GET("product")
    suspend fun loadProduct(): Response<List<ProductResponse>>

    @GET("product/all/{id}")
    suspend fun loadProducts(@Path("id")id : Boolean): Response<List<ProductResponse>>

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id")id : String): Response<ProductResponse>

    @PUT("product")
    suspend fun updateProduct(@Body productResponse: ProductResponse): Response<ProductResponse>

    @GET("product/img/{id}")
    suspend fun loadProductImage(@Path("id")id : String): Response<List<ImageMoreResponse>>

}