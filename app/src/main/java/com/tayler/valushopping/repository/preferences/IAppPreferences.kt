package com.tayler.valushopping.repository.preferences

import com.tayler.valushopping.entity.UserModel

interface IAppPreferences {
    fun saveToken(value : String )

    fun getToken() : Boolean

    fun saveUser(value : UserModel):UserModel

    fun getUser() : UserModel

    fun login(user : String,key : String):Boolean

}