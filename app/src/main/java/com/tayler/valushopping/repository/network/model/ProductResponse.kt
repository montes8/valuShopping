package com.tayler.valushopping.repository.network.model

import com.google.gson.annotations.SerializedName
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.PRICE_DEFAULT

data class ProductResponse (
    @SerializedName("uid")
    var uid : String? = EMPTY_VALE,
    @SerializedName("name")
    var name : String? = EMPTY_VALE,
    @SerializedName("description")
    var description : String? = EMPTY_VALE,
    @SerializedName("type")
    var type : String? = EMPTY_VALE,
    @SerializedName("price")
    var price : String? = PRICE_DEFAULT,
    @SerializedName("priceTwo")
    var priceTwo : String? = PRICE_DEFAULT,
    @SerializedName("state")
    var state : Boolean? = true,
    @SerializedName("img")
    var img : String? = EMPTY_VALE
)