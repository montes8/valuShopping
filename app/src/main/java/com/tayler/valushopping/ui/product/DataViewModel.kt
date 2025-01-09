package com.tayler.valushopping.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tayler.valushopping.BuildConfig
import com.tayler.valushopping.repository.network.api.DataNetwork
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.repository.preferences.api.AppPreferences
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.utils.EMPTY_VALE
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DataViewModel  @Inject constructor(
    private val dataNetwork: DataNetwork,
    private val  appPreferences: AppPreferences,
): BaseViewModel(){

    val successProductLiveData        : LiveData<ProductResponse> get()   = _successProductLiveData
    private val _successProductLiveData    = MutableLiveData<ProductResponse>()


    fun saveProduct(data : ProductResponse){
        execute(false) {
            val responseImage = dataNetwork.saveImage(File(data.img?: EMPTY_VALE))
            data.img = "${BuildConfig.BASE_URL}uploads/imgs/$responseImage"
            val response = dataNetwork.saveProduct(data)
            _successProductLiveData.postValue(response)
        }
    }

}