package com.tayler.valushopping.ui.product

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.manager.camera.UiTayCameraManager
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.gb.vale.uitaylibrary.utils.uiTayShowToast
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityProductBinding
import com.tayler.valushopping.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : BaseActivity(),UiTayCameraManager.CameraControllerListener {

    private lateinit var binding: ActivityProductBinding
    private var managerCamera : UiTayCameraManager? = null

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, ProductActivity::class.java))
        }
    }
    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        binding.lifecycleOwner = this    }

    override fun setUpView() {
        configInit()
        configAction()
    }

    private fun configAction(){
        binding.imgProduct.setOnClickUiTayDelay {
            managerCamera?.doCamera("valeImageProduct")
        }
    }
    override fun observeViewModel() {
    }

    private fun configInit(){
        managerCamera = UiTayCameraManager(this,"product",this)
    }

    override fun onCameraPermissionDenied() {
        uiTayShowToast("Necesitas este permiso para agregar imagen")
    }

    override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        binding.imgProduct.setImageBitmap(img)
    }
}