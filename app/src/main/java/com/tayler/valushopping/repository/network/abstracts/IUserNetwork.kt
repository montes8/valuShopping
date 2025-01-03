package com.tayler.valushopping.repository.network.abstracts

import com.tayler.valushopping.repository.network.model.ParamResponse

interface IUserNetwork {
    suspend fun loadParam():ParamResponse
}