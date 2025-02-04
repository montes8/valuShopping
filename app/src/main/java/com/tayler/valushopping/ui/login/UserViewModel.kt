package com.tayler.valushopping.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tayler.valushopping.entity.singleton.AppDataVale
import com.tayler.valushopping.repository.network.api.UserNetwork
import com.tayler.valushopping.repository.network.model.response.UserResponse
import com.tayler.valushopping.repository.preferences.api.AppPreferences
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.utils.EMPTY_VALE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel  @Inject constructor(
    private val  appPreferences: AppPreferences,
    private val  userNetwork: UserNetwork,

): BaseViewModel(){

    val successUserLiveData        : LiveData<UserResponse> get()   = _successUserLiveData
    private val _successUserLiveData    = MutableLiveData<UserResponse>()

    val successUserImgLiveData        : LiveData<UserResponse> get()   = _successUserImgLiveData
    private val _successUserImgLiveData    = MutableLiveData<UserResponse>()
    val successLoginLiveData        : LiveData<Boolean> get()   = _successLoginLiveData
    private val _successLoginLiveData    = MutableLiveData<Boolean>()

    fun loadUser() = executeLiveData{ appPreferences.getUser()}

    fun loadSession() =  appPreferences.getToken()

    fun login(user : String,key : String){
        execute {
                val response = userNetwork.login(user,key)
                appPreferences.saveToken(response.token)
                 val useSave = appPreferences.getUser()
                 useSave.uid = response.userValid?.uid?: EMPTY_VALE
                 useSave.rol = response.userValid?.rol?: EMPTY_VALE
                 val userUpdate = appPreferences.saveUser(useSave)
                 AppDataVale.user = userUpdate
            _successLoginLiveData.postValue(true)
        }
    }
    fun saveUser(user : UserResponse){
        execute {
            val response = appPreferences.saveUser(user)
            _successUserLiveData.postValue(response)
        }
    }

    fun saveUserImg(user : UserResponse){
        execute {
            val response = appPreferences.saveUser(user)
            _successUserImgLiveData.postValue(response)
        }
    }
    fun logout(){
        execute(false) {
            appPreferences.saveToken(EMPTY_VALE)
        }
    }
}