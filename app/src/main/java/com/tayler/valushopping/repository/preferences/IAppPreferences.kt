package com.tayler.valushopping.repository.preferences

import com.tayler.valushopping.repository.network.model.response.UserResponse

interface IAppPreferences {
    fun saveToken(value : String )

    fun getToken() : Boolean

    fun saveUser(value : UserResponse):UserResponse

    fun getUser() : UserResponse
}