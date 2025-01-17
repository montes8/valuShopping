package com.tayler.valushopping.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tayler.valushopping.BuildConfig
import com.tayler.valushopping.repository.network.api.DataNetwork
import com.tayler.valushopping.repository.network.model.ProductResponse
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

    val successUpdateProductLiveData        : LiveData<ProductResponse> get()   = _successUpdateProductLiveData
    private val _successUpdateProductLiveData    = MutableLiveData<ProductResponse>()


    fun saveProduct(data : ProductResponse){
        execute(false) {
            val responseImage = dataNetwork.saveImage(File(data.img?: EMPTY_VALE))
            data.img = "${BuildConfig.BASE_URL}uploads/imgs/${responseImage.nameImage}"
            val response = dataNetwork.saveProduct(data)
            _successProductLiveData.postValue(response)
        }
    }

    fun loadProduct(){
        execute(false) {
            val response = dataNetwork.loadProduct()
            _successLoadProductLiveData.postValue(response)
        }
    }

    fun updateProduct(data : ProductResponse){
        execute(false) {
            val response = dataNetwork.updateProduct(data)
            _successUpdateProductLiveData.postValue(response)
        }
    }

    fun loadDeleteProduct(id:String){
        execute(false) {
            val response = dataNetwork.deleteProduct(id)
            _successDeleteLiveData.postValue(response)
        }
    }

}