package com.tayler.valushopping.repository.network.model.response

import com.google.gson.annotations.SerializedName
import com.tayler.valushopping.utils.EMPTY_VALE

data class UserResponse (
    @SerializedName("uid")
    var uid : String? = EMPTY_VALE,
    @SerializedName("nameUser")
    var nameUser : String? = EMPTY_VALE,
    @SerializedName("names")
    var names : String? = EMPTY_VALE,
    @SerializedName("lastName")
    var lastName : String?  = EMPTY_VALE,
    @SerializedName("document")
    var document : String? = EMPTY_VALE,
    @SerializedName("email")
    var email : String? = EMPTY_VALE,
    @SerializedName("phone")
    var phone : String? = EMPTY_VALE,
    @SerializedName("address")
    var address : String? = EMPTY_VALE,
    @SerializedName("img")
    var img : String? = EMPTY_VALE,
    @SerializedName("imgBanner")
    var imgBanner : String? = EMPTY_VALE,
    @SerializedName("rol")
    var rol : String? = EMPTY_VALE
)