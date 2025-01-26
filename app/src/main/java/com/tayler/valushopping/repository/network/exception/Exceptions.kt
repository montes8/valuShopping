package com.tayler.valushopping.repository.network.exception

import com.google.gson.annotations.SerializedName
import com.tayler.valushopping.repository.DEFAULT_CODE
import com.tayler.valushopping.repository.ERROR_MESSAGE_GENERAL
import com.tayler.valushopping.repository.ERROR_TITLE_GENERAL
import com.tayler.valushopping.utils.EMPTY_VALE

data class ApiException(val code: Int = 0,val title: String = EMPTY_VALE, val messageApi: String = EMPTY_VALE): Exception()

class GenericException : Exception()

class MyNetworkException : Exception()

class UnAuthorizedException: Exception()

data class CompleteErrorModel(
    @SerializedName("errorCode")
    var code: Int? = DEFAULT_CODE,
    @SerializedName("title")
    val title: String? = ERROR_TITLE_GENERAL,
    @SerializedName("description")
    val description: String?= ERROR_MESSAGE_GENERAL
) : Exception(description){

    fun getApiException(): Exception {
        return ApiException( this.code?:0, this.title?: ERROR_MESSAGE_GENERAL,this.description?:ERROR_MESSAGE_GENERAL)
    }
}