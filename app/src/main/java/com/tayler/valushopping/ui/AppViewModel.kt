package com.tayler.valushopping.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tayler.valushopping.repository.network.api.UserNetwork
import com.tayler.valushopping.repository.network.model.ParamResponse
import com.tayler.valushopping.repository.preferences.api.AppPreferences
import com.tayler.valushopping.utils.EMPTY_VALE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel  @Inject constructor(
    private val userNetwork: UserNetwork,
    private val  appPreferences: AppPreferences,
): BaseViewModel(){


    val successSessionLiveData        : LiveData<Boolean> get()   = _successSessionLiveData
    private val _successSessionLiveData    = MutableLiveData<Boolean>()

    val successParamLiveData        : LiveData<ParamResponse> get()   = _successParamLiveData
    private val _successParamLiveData    = MutableLiveData<ParamResponse>()


    fun loadParam(){
        executeNotProgress {
            val response = userNetwork.loadParam()
            _successParamLiveData.postValue(response)
        }
    }

    fun logout(){
        executeNotProgress {
            appPreferences.saveToken(EMPTY_VALE)
        }
    }

    fun session() = executeLiveData {
        appPreferences.getToken()
    }
}