package com.tayler.valushopping.entity

import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.NAME_VALE

data class UserModel (
    var id : String = EMPTY_VALE,
    var user : String = EMPTY_VALE,
    var password : String = EMPTY_VALE,
    var names : String = NAME_VALE,
    var lastName : String = EMPTY_VALE,
    var document : String = EMPTY_VALE,
    var email : String = EMPTY_VALE,
    var phone : String = EMPTY_VALE,
    var addressUser : String = EMPTY_VALE,
    var img : String = EMPTY_VALE,
    var imgBanner : String = EMPTY_VALE,
    var dataSave : Boolean = false
)