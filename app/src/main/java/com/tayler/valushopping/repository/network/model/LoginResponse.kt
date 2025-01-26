package com.tayler.valushopping.repository.network.model

import com.google.gson.annotations.SerializedName
import com.tayler.valushopping.repository.network.model.response.UserResponse

data class LoginResponse (
    @SerializedName("userValid")
    val userValid : UserResponse? = null,
    @SerializedName("token")
    val token : String
)