package com.tayler.valushopping.entity.singleton

import com.tayler.valushopping.repository.network.model.ParamResponse
import com.tayler.valushopping.repository.network.model.response.UserResponse

object AppDataVale {

    var paramData :ParamResponse = ParamResponse()
    var user :UserResponse = UserResponse()
}