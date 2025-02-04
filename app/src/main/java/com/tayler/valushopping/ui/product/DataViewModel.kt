package com.tayler.valushopping.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tayler.valushopping.BuildConfig
import com.tayler.valushopping.entity.singleton.AppDataVale
import com.tayler.valushopping.repository.network.api.DataNetwork
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.repository.network.model.request.ProductImageRequest
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

    val successMoreImageLiveData        : LiveData<ImageMoreResponse> get()   = _successMoreImageLiveData
    private val _successMoreImageLiveData    = MutableLiveData<ImageMoreResponse>()


    fun saveProduct(data : ProductResponse){
        execute {
            val responseImage = dataNetwork.saveImage(File(data.img?: EMPTY_VALE))
            data.img = responseImage.nameImage
            data.admin = AppDataVale.user.rol == "ADMIN"
            data.idUser = AppDataVale.user.uid
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

    fun loadMoreImageProduct(id:String,loading : Boolean = true){
        execute(loading) {
            val response = dataNetwork.loadProductImage(id)
            _successProductImageLiveData.postValue(response)
        }
    }

    fun loadDeleteMoreProductImage(id:String){
        execute {
            val response = dataNetwork.deleteProductImage(id)
            _successMoreImageLiveData.postValue(response)
        }
    }

    fun loadSaveMoreProductImage(data:ProductResponse,file: String){
        execute {
            val responseImage = dataNetwork.saveImageMore(File(file),data.phone?: EMPTY_VALE)
            val request = ProductImageRequest(
                responseImage.nameImage,
                data.uid,
                data.idUser,"${BuildConfig.BASE_URL}/uploads/productMore/${data.phone}/${responseImage.nameImage}",
                data.phone
            )
            val response = dataNetwork.saveProductDBImages(request)
            _successMoreImageLiveData.postValue(response)
        }
    }

}