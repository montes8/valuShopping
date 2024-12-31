package com.tayler.valushopping.repository.preferences

interface IAppPreferences {
    fun saveToken(value : String )

    fun getToken() : Boolean

}