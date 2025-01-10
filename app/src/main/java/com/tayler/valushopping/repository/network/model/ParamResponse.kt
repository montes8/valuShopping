package com.tayler.valushopping.repository.network.model

import com.google.gson.annotations.SerializedName
import com.tayler.valushopping.utils.DESCRIPTION_MOVIE_DEFAULT
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.ID_MOVIE_DEFAULT
import com.tayler.valushopping.utils.TITLE_MOVIE_DEFAULT

data class ParamResponse (
    @SerializedName("uid")
    var uid : String? = EMPTY_VALE,
    @SerializedName("title")
    var title : String? = TITLE_MOVIE_DEFAULT,
    @SerializedName("description")
    var description : String? = DESCRIPTION_MOVIE_DEFAULT,
    @SerializedName("idMovie")
    var idMovie : String? = ID_MOVIE_DEFAULT,
    @SerializedName("enableCategory")
    var enableCategory : Boolean? = false,
    @SerializedName("phone")
    var phone : String? = ID_MOVIE_DEFAULT,
    @SerializedName("linkFacebook")
    var linkFacebook : String? = ID_MOVIE_DEFAULT
)