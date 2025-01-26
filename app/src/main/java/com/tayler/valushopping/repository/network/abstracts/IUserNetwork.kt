package com.tayler.valushopping.repository.network.abstracts

import com.tayler.valushopping.repository.network.model.LoginResponse
import com.tayler.valushopping.repository.network.model.ParamResponse

interface IUserNetwork {
    suspend fun loadParam():ParamResponse

    suspend fun login(user:String,key:String): LoginResponse

    suspend fun saveParam(param : ParamResponse):ParamResponse

    suspend fun updateParam(param : ParamResponse):ParamResponse
}