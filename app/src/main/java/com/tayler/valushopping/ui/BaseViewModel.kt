package com.tayler.valushopping.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    val errorLiveData = MutableLiveData<Throwable>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val shimmerLiveData = MutableLiveData<Boolean>()


    fun execute(loading : Boolean = true,func: suspend () -> Unit)=
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadingLiveData.postValue(loading)
                shimmerLiveData.postValue(!loading)
                func()
                loadingLiveData.postValue(false)
                shimmerLiveData.postValue(false)
            } catch (ex: Exception) {
                ex.printStackTrace()
                loadingLiveData.postValue(false)
                shimmerLiveData.postValue(false)
                Log.d("servicess","errorLiveData")

                errorLiveData.postValue(ex)
            }
        }

    fun <T> executeLiveData(func: suspend () -> T) =
        liveData(Dispatchers.IO) {
            try {
                val value = func()
                emit(value)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

}