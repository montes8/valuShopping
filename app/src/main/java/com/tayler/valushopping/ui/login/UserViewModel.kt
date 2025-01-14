package com.tayler.valushopping.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tayler.valushopping.entity.UserModel
import com.tayler.valushopping.repository.preferences.api.AppPreferences
import com.tayler.valushopping.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel  @Inject constructor(
    private val  appPreferences: AppPreferences,
): BaseViewModel(){

    val successUserLiveData        : LiveData<UserModel> get()   = _successUserLiveData
    private val _successUserLiveData    = MutableLiveData<UserModel>()

    fun loadUser() = executeLiveData{ appPreferences.getUser()}

    fun saveUser(user : UserModel){
        execute(false) {
            val response = appPreferences.saveUser(user)
            _successUserLiveData.postValue(response)
        }
    }
}