package com.tayler.valushopping.repository.network.model.response

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("uid")
    val uid : String? = null,
    @SerializedName("nameUser")
    val nameUser : String? = null,
    @SerializedName("names")
    val names : String,
    @SerializedName("lastName")
    val lastName : String? = null,
    @SerializedName("document")
    val document : String,
    @SerializedName("email")
    val email : String? = null,
    @SerializedName("phone")
    val phone : String,
    @SerializedName("address")
    val address : String? = null,
    @SerializedName("rol")
    val rol : String
)