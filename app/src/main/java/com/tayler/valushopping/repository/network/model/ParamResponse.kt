package com.tayler.valushopping.repository.network.model

import com.google.gson.annotations.SerializedName

data class ParamResponse (
    @SerializedName("uid")
    var id : String?,
    @SerializedName("title")
    var title : String?,
    @SerializedName("description")
    var description : String?,
    @SerializedName("enableCategory")
    var enableRegister : Boolean?
)