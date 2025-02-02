package com.tayler.valushopping.repository.network.model.request

import com.google.gson.annotations.SerializedName

class LoginRequest (
    @SerializedName("nameUser")
    val nameUser: String,
    @SerializedName("password")
    val password : String
)