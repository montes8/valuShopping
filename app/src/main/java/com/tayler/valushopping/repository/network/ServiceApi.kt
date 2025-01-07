package com.tayler.valushopping.repository.network

import com.tayler.valushopping.repository.network.model.ImageResponse
import com.tayler.valushopping.repository.network.model.ParamResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ServiceApi {
    @GET("config/param")
    suspend fun loadParam(): Response<ParamResponse>?

    @POST("config/param")
    suspend fun saveParam(@Body paramResponse: ParamResponse): Response<ParamResponse>

    @PUT("config/param/{id}")
    suspend fun updateParam(@Path("id")id : String,
                            @Body paramResponse: ParamResponse): Response<ParamResponse>

    @Multipart
    @POST("api/uploads")
    suspend fun saveImage(
        @Part file : MultipartBody.Part): Response<ImageResponse>
}