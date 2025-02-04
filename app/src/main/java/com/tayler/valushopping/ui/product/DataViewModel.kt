package com.tayler.valushopping.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tayler.valushopping.BuildConfig
import com.tayler.valushopping.entity.singleton.AppDataVale
import com.tayler.valushopping.repository.network.api.DataNetwork
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.repository.network.model.response.ImageMoreResponse
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.utils.EMPTY_VALE
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DataViewModel  @Inject constructor(
    private val dataNetwork: DataNetwork
): BaseViewModel(){

    val successProductLiveData        : LiveData<ProductResponse> get()   = _successProductLiveData
    private val _successProductLiveData    = MutableLiveData<ProductResponse>()
    val successLoadProductLiveData        : LiveData<List<ProductResponse>> get()   = _successLoadProductLiveData
    private val _successLoadProductLiveData    = MutableLiveData<List<ProductResponse>>()
    val successDeleteLiveData        : LiveData<ProductResponse> get()   = _successDeleteLiveData
    private val _successDeleteLiveData    = MutableLiveData<ProductResponse>()

    val successProductImageLiveData        : LiveData<List<ImageMoreResponse>> get()   = _successProductImageLiveData
    private val _successProductImageLiveData    = MutableLiveData<List<ImageMoreResponse>>()


    fun saveProduct(data : ProductResponse){
        execute {
            val responseImage = dataNetwork.saveImage(File(data.img?: EMPTY_VALE))
            data.img = responseImage.nameImage
            data.admin = AppDataVale.user.rol == "ADMIN"
            data.idUser = AppDataVale.user.uid
            Log.d( "userrrr",AppDataVale.user.toString())
            data.url = "${BuildConfig.BASE_URL}/uploads/uploads/product/$responseImage"
            val response = dataNetwork.saveProduct(data)
            _successProductLiveData.postValue(response)
        }
    }

    fun loadProduct(all : Boolean = false,admin:Boolean = false){
        execute(false) {
            val response = dataNetwork.loadProduct(all,admin)
            _successLoadProductLiveData.postValue(response)
        }
    }

    fun updateProduct(data : ProductResponse){
        execute {
            val response = dataNetwork.updateProduct(data)
            _successProductLiveData.postValue(response)
        }
    }

    fun loadDeleteProduct(id:String){
        execute {
            val response = dataNetwork.deleteProduct(id)
            _successDeleteLiveData.postValue(response)
        }
    }

    fun loadMoreImageProduct(id:String){
        execute {
            val response = dataNetwork.loadProductImage(id)
            _successProductImageLiveData.postValue(response)
        }
    }

}