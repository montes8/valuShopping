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

    val successParamLiveData        : LiveData<ParamResponse> get()   = _successParamLiveData
    private val _successParamLiveData    = MutableLiveData<ParamResponse>()
    val successUpdateParamLiveData        : LiveData<ParamResponse> get()   = _successUpdateParamLiveData
    private val _successUpdateParamLiveData    = MutableLiveData<ParamResponse>()


    fun loadParam(){
        execute(false) {
            val response = userNetwork.loadParam()
            _successParamLiveData.postValue(response)
        }
    }

    fun updateParam(param: ParamResponse){
        execute(false) {
            val response = if(param.uid?.isEmpty() == true)userNetwork.saveParam(param) else
                userNetwork.updateParam(param)

            _successUpdateParamLiveData.postValue(response)
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