package com.tayler.valushopping.repository.preferences.api

import com.google.gson.Gson
import com.tayler.valushopping.entity.UserModel
import com.tayler.valushopping.repository.PREFERENCE_TOKEN
import com.tayler.valushopping.repository.PREFERENCE_USER
import com.tayler.valushopping.repository.preferences.IAppPreferences
import com.tayler.valushopping.repository.preferences.manager.PreferencesManager
import javax.inject.Inject

class AppPreferences @Inject constructor(private val preferenceManager : PreferencesManager):
    IAppPreferences {

    override fun saveToken(value : String ) = preferenceManager.setValue(PREFERENCE_TOKEN,value)

    override fun getToken() = preferenceManager.getString(PREFERENCE_TOKEN).isNotEmpty()
    override fun saveUser(value: UserModel):UserModel {
        val userDataAsString = Gson().toJson(value)
        preferenceManager.setValue(PREFERENCE_USER,userDataAsString)
        return  getUser()
    }

    override fun getUser(): UserModel {
        return retrieveSavedString().toUserModel()
    }

    override fun login(user: String, key: String): Boolean {
        val login = user == "Valeria" && key == "18101810"
        if (login){saveToken("20141018201410182014101820141018")}
        return  login
    }

    private fun String.toUserModel(): UserModel {
        return try {
            Gson().fromJson(this, UserModel::class.java)
        }catch (e:Exception){
            UserModel()
        }
    }

    private fun retrieveSavedString() = preferenceManager.getString(PREFERENCE_USER)

}