package com.tayler.valushopping.repository.network.api

import com.tayler.valushopping.repository.network.ServiceApi
import com.tayler.valushopping.repository.network.abstracts.IUserNetwork
import com.tayler.valushopping.repository.network.abstracts.base.BaseNetwork
import com.tayler.valushopping.repository.network.model.LoginRequest
import com.tayler.valushopping.repository.network.model.LoginResponse
import com.tayler.valushopping.repository.network.model.ParamResponse
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.toCompleteErrorModel
import com.tayler.valushopping.utils.validateBody
import com.tayler.valushopping.utils.validateData
import javax.inject.Inject

class UserNetwork @Inject constructor(private val serviceApi : ServiceApi, private val base : BaseNetwork):
    IUserNetwork {
    override suspend fun loadParam(): ParamResponse {
        return base.executeWithConnection {
            var model  = ParamResponse()
            try {
                val response  = serviceApi.loadParam()
                if (response.validateData()) {
                    model = response.validateBody()
                }
            }catch (e:Exception){e.printStackTrace()}

            model
         }
    }

    override suspend fun login(user: String, key: String) : LoginResponse{
        return base.executeWithConnection {
            var model : LoginResponse?  = null
            val response  = serviceApi.login(LoginRequest(user,key))
            if (response.validateData()) {
                model = response.validateBody()
            }
            model?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }

    override suspend fun saveParam(param: ParamResponse): ParamResponse {
        return base.executeWithConnection {
            var model  : ParamResponse?  = null
                val response  = serviceApi.saveParam(param)
                if (response.validateData()) {
                    model = response.validateBody()
                }
            model?: throw response.errorBody().toCompleteErrorModel(response.code())

        }
    }

    override suspend fun updateParam(param: ParamResponse): ParamResponse {
        return base.executeWithConnection {
            var model  : ParamResponse?  = null
            val response  = serviceApi.updateParam(param.uid?: EMPTY_VALE,param)
            if (response.validateData()) {
                model = response.validateBody()
            }
            model?: throw response.errorBody().toCompleteErrorModel(response.code())
        }
    }
}