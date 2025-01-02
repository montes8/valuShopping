package com.tayler.valushopping.entity

import com.google.gson.annotations.SerializedName

class ItemModel(
    @SerializedName("id")
    var id : Int,
    @SerializedName("title")
    var title : String,
    @SerializedName("icon")
    var icon : String
)