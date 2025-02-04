package com.tayler.valushopping.ui.product.images

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModel
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModelCustom
import com.gb.vale.uitaylibrary.manager.camera.UiTayCameraManager
import com.gb.vale.uitaylibrary.utils.showUiTayDialog
import com.gb.vale.uitaylibrary.utils.uiTayParcelable
import com.gb.vale.uitaylibrary.utils.uiTayShowToast
import com.gb.vale.uitaylibrary.utils.uiTayVisibilityDuo
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityAddImagesBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.repository.network.model.response.ImageMoreResponse
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.product.DataViewModel
import com.tayler.valushopping.ui.product.images.adapter.ListImageAdapter
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.dialogZoom
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddImagesActivity : BaseActivity(), UiTayCameraManager.CameraControllerListener {
    private val viewModel: DataViewModel by viewModels()

    private lateinit var binding: ActivityAddImagesBinding
    private var managerCamera : UiTayCameraManager? = null
    private var dataProduct = ProductResponse()
    private var adapterImages = ListImageAdapter()
    companion object {
        fun newInstance(context: Context, product : ProductResponse = ProductResponse()) {
            val intent = Intent(context, AddImagesActivity::class.java)
            intent.putExtra(AddImagesActivity::class.java.name,product)
            context.startActivity(intent)
        }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_images)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configInit()
        configAction()
        loadServiceImages()
    }

    private fun configInit(){
        dataProduct = intent.uiTayParcelable(AddImagesActivity::class.java.name)?:ProductResponse()
        binding.imagesAdapter = adapterImages
        managerCamera = UiTayCameraManager(this,"productMore",this)
    }


    private fun configAction(){
        binding.tbImagesMore.setOnClickTayBackListener{onToBack()}
       binding.tbImagesMore.setOnClickTayMenuListener{
           managerCamera?.doCamera("imageMoreProduct")
       }
    }

    override fun observeViewModel() {
        viewModel.successProductImageLiveData.observe(this){
            configListImages(it)
        }

        viewModel.shimmerLiveData.observe(this) {
            binding.shimmerListImages.uiTayVisibilityDuo(it, binding.ctnListImages)
        }

        viewModel.successMoreImageLiveData.observe(this){
            loadServiceImages()
        }
    }

    private  fun loadServiceImages(){
        viewModel.loadMoreImageProduct(dataProduct.uid?: EMPTY_VALE,false)
    }
    private fun configListImages(list : List<ImageMoreResponse>){
        binding.rvListImages.uiTayVisibilityDuo(list.isNotEmpty(),binding.ctnListImageEmpty)
        adapterImages.imageList = list
        adapterImages.onClickDelete = {
            onClickDelete(it.uid?: EMPTY_VALE)

        }
        adapterImages.onClickImage = {
            dialogZoom(it)
        }
    }

    override fun onCameraPermissionDenied() {
        uiTayShowToast(getString(R.string.error_camera))    }

    override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        viewModel.loadSaveMoreProductImage(dataProduct,path)
    }

    private fun onClickDelete(id:String){
        showUiTayDialog(model = UiTayDialogModel(title = getString(R.string.text_title_delete_image),
            subTitle = getString(R.string.sub_text_title_delete_image),
            styleCustom =
            UiTayDialogModelCustom(btnAcceptSolidColor = R.color.red, btnAcceptStrokeColor = R.color.red)
        )
        ){
            if (it){
                viewModel.loadDeleteMoreProductImage(id)
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}