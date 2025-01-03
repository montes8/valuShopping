package com.tayler.valushopping.repository.network.model

import com.google.gson.annotations.SerializedName
import com.tayler.valushopping.utils.EMPTY_VALE

data class ParamResponse (
    @SerializedName("uid")
    var uid : String? = EMPTY_VALE,
    @SerializedName("title")
    var title : String? = EMPTY_VALE,
    @SerializedName("description")
    var description : String? = EMPTY_VALE,
    @SerializedName("idMovie")
    var idMovie : String? = EMPTY_VALE,
    @SerializedName("enableOther")
    var enableOther : Boolean? = false
)