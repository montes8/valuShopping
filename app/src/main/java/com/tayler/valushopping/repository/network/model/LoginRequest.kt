package com.tayler.valushopping.repository.network.model

import com.google.gson.annotations.SerializedName

class LoginRequest (
    @SerializedName("nameUser")
    val nameUser: String,
    @SerializedName("password")
    val password : String
)