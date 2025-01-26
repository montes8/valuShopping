package com.tayler.valushopping.entity.singleton

import com.tayler.valushopping.entity.UserModel
import com.tayler.valushopping.repository.network.model.ParamResponse

object AppDataVale {

    var paramData :ParamResponse = ParamResponse()
    var user :UserModel = UserModel()
}