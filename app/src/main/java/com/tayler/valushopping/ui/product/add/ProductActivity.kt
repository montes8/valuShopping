package com.tayler.valushopping.ui.product.add

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.manager.camera.UiTayCameraManager
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.gb.vale.uitaylibrary.utils.uiTayFormatDecimal
import com.gb.vale.uitaylibrary.utils.uiTayShowToast
import com.gb.vale.uitaylibrary.utils.uiTayValidatePhoneFormat
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityProductBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.home.HomeActivity
import com.tayler.valushopping.ui.product.DataViewModel
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.TYPE_CLOTHES
import com.tayler.valushopping.utils.TYPE_OTHER
import com.tayler.valushopping.utils.successDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : BaseActivity(),UiTayCameraManager.CameraControllerListener {
    private val viewModel: DataViewModel by viewModels()
    private lateinit var binding: ActivityProductBinding
    private var managerCamera : UiTayCameraManager? = null
    private var dataProduct = ProductResponse()
    private var fileImage = EMPTY_VALE
    private var type = 0
    private var gender = 0
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
        configChange()
    }

    override fun observeViewModel() {
        viewModel.successProductLiveData.observe(this){
            this.successDialog{
                HomeActivity.newInstance(this)
            }
        }
    }
    private fun loadService(){
        mapperData()
        viewModel.saveProduct(dataProduct)
    }
    private fun configAction(){
        binding.imgProduct.setOnClickUiTayDelay {
            managerCamera?.doCamera("valeImageProduct")
        }
        binding.tbProduct.setOnClickTayBackListener{onToBack()}
        binding.btnSaveProduct.setOnClickTayBtnListener{ loadService()}
    }

    private fun configChange(){
        binding.editUnit.setOnChangeTayEditLayoutListener{ enableBtn()}
        binding.editTotal.setOnChangeTayEditLayoutListener{ enableBtn()}
        binding.editNameProduct.setOnChangeTayEditListener{ enableBtn()}
        binding.editDescriptionProduct.addTextChangedListener{ enableBtn()}
    }

    private fun configInit(){
        managerCamera = UiTayCameraManager(this,"product",this)
        configRgGender()
        configRgType()
    }

    override fun onCameraPermissionDenied() {
        uiTayShowToast(getString(R.string.error_camera))
    }

    override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        binding.imgProduct.setImageBitmap(img)
        fileImage = path
        enableBtn()
    }

    private fun enableBtn(){
        var flagEnable = 0
        flagEnable  += if(binding.editNameProduct.uiTayLabelEdit.length > 4) 0 else 1
        flagEnable += if(binding.editDescriptionProduct.text.toString().length > 20) 0 else 1
        flagEnable  += if(binding.editUnit.uiTayLText.length > 1) 0 else 1
        flagEnable  += if(fileImage.isNotEmpty()) 0 else 1
        flagEnable  += if(binding.editPhoneProduct.uiTayLabelEdit.uiTayValidatePhoneFormat()) 0 else 1
        binding.btnSaveProduct.tayBtnEnable = flagEnable == 0
    }

    private fun mapperData(){
        dataProduct.name = binding.editNameProduct.uiTayLabelEdit
        dataProduct.description = binding.editDescriptionProduct.text.toString()
        dataProduct.price = binding.editUnit.uiTayLText.uiTayFormatDecimal()
        dataProduct.priceTwo = binding.editTotal.uiTayLText.uiTayFormatDecimal()
        dataProduct.phone = binding.editPhoneProduct.uiTayLabelEdit
        dataProduct.img =  fileImage
        dataProduct.state = binding.cbState.isChecked
        dataProduct.type = type.toString()
        dataProduct.gender = gender.toString()
        dataProduct.principal = binding.cbPrincipal.isChecked
        dataProduct.category = if(type != 5) TYPE_CLOTHES else TYPE_OTHER
    }

    private fun configRgGender()= with(binding){
        rgGender.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rbFemale->{ gender = 0 }
                R.id.rbMale->{ gender = 1 }
                R.id.rbUnisex->{ gender = 2 }
            }
            enableBtn()
        }
    }

    private fun configRgType()= with(binding){
        rgType.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rbImitationJewelry->{type = 0}
                R.id.rbPole->{ type = 1 }
                R.id.rbPants->{ type = 2  }
                R.id.rbFootwear->{ type = 3  }
                R.id.rbSkirt->{ type = 4 }
                R.id.rbGarments->{ type = 5  }
                R.id.rbOther->{ type = 6  }
                R.id.rbAccessories->{type = 7}
            }
            enableBtn()
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}