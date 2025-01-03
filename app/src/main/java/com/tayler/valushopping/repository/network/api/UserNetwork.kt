package com.tayler.valushopping.repository.network.api

import com.tayler.valushopping.repository.network.ServiceApi
import com.tayler.valushopping.repository.network.abstracts.IUserNetwork
import com.tayler.valushopping.repository.network.abstracts.base.BaseNetwork
import com.tayler.valushopping.repository.network.model.ParamResponse
import com.tayler.valushopping.utils.validateBody
import javax.inject.Inject

class UserNetwork @Inject constructor(private val serviceApi : ServiceApi, private val base : BaseNetwork):
    IUserNetwork {
    override suspend fun loadParam(): ParamResponse {
        return base.executeWithConnection {
            var model : ParamResponse ? = null
            val response = serviceApi.loadParam()
            if (response.isSuccessful && response.body() != null) {
                model = response.validateBody()
            }
            model?:ParamResponse()
         }
    }
}