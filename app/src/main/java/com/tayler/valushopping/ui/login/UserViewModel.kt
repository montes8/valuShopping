package com.tayler.valushopping.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gb.vale.uitaylibrary.utils.uiTayHandler
import com.tayler.valushopping.entity.UserModel
import com.tayler.valushopping.repository.preferences.api.AppPreferences
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.utils.EMPTY_VALE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel  @Inject constructor(
    private val  appPreferences: AppPreferences,

): BaseViewModel(){

    val successUserLiveData        : LiveData<UserModel> get()   = _successUserLiveData
    private val _successUserLiveData    = MutableLiveData<UserModel>()
    val successLoginLiveData        : LiveData<Boolean> get()   = _successLoginLiveData
    private val _successLoginLiveData    = MutableLiveData<Boolean>()

    fun loadUser() = executeLiveData{ appPreferences.getUser()}

    fun loadSession() =  appPreferences.getToken()

    fun login(user : String,key : String){
        execute {
            uiTayHandler(time = 2) {
                val response = appPreferences.login(user,key)
                _successLoginLiveData.postValue(response)
            }
        }
    }
    fun saveUser(user : UserModel){
        execute {
            val response = appPreferences.saveUser(user)
            _successUserLiveData.postValue(response)
        }
    }
    fun logout(){
        execute(false) {
            appPreferences.saveToken(EMPTY_VALE)
        }
    }
}