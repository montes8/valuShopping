package com.tayler.valushopping.repository.preferences.api

import com.google.gson.Gson
import com.tayler.valushopping.repository.PREFERENCE_TOKEN
import com.tayler.valushopping.repository.PREFERENCE_USER
import com.tayler.valushopping.repository.network.model.response.UserResponse
import com.tayler.valushopping.repository.preferences.IAppPreferences
import com.tayler.valushopping.repository.preferences.manager.PreferencesManager
import javax.inject.Inject

class AppPreferences @Inject constructor(private val preferenceManager : PreferencesManager):
    IAppPreferences {

    override fun saveToken(value : String ) = preferenceManager.setValue(PREFERENCE_TOKEN,value)

    override fun getToken() = preferenceManager.getString(PREFERENCE_TOKEN).isNotEmpty()
    override fun saveUser(value: UserResponse):UserResponse {
        val userDataAsString = Gson().toJson(value)
        preferenceManager.setValue(PREFERENCE_USER,userDataAsString)
        return  getUser()
    }

    override fun getUser(): UserResponse {
        return retrieveSavedString().toUserModel()
    }

    private fun String.toUserModel(): UserResponse {
        return try {
            Gson().fromJson(this, UserResponse::class.java)
        }catch (e:Exception){
            UserResponse()
        }
    }

    private fun retrieveSavedString() = preferenceManager.getString(PREFERENCE_USER)

}