package com.tayler.valushopping.repository.preferences.api

import com.tayler.valushopping.repository.PREFERENCE_TOKEN
import com.tayler.valushopping.repository.preferences.IAppPreferences
import com.tayler.valushopping.repository.preferences.manager.PreferencesManager
import javax.inject.Inject

class AppPreferences @Inject constructor(private val preferenceManager : PreferencesManager):
    IAppPreferences {

    override fun saveToken(value : String ) = preferenceManager.setValue(PREFERENCE_TOKEN,value)

    override fun getToken() = preferenceManager.getString(PREFERENCE_TOKEN).isNotEmpty()
}