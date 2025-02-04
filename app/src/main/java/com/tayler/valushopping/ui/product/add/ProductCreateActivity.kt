package com.tayler.valushopping.ui.product.add

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.extra.UITayStyleTbIcon
import com.gb.vale.uitaylibrary.manager.camera.UiTayCameraManager
import com.gb.vale.uitaylibrary.utils.EventUiTay
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.gb.vale.uitaylibrary.utils.uiTayFormatDecimal
import com.gb.vale.uitaylibrary.utils.uiTayLoadUrl
import com.gb.vale.uitaylibrary.utils.uiTayParcelable
import com.gb.vale.uitaylibrary.utils.uiTayShowToast
import com.gb.vale.uitaylibrary.utils.uiTayTryCatch
import com.gb.vale.uitaylibrary.utils.uiTayValidatePhoneFormat
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityProductBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.product.DataViewModel
import com.tayler.valushopping.ui.product.images.AddImagesActivity
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.TYPE_CLOTHES
import com.tayler.valushopping.utils.TYPE_OTHER
import com.tayler.valushopping.utils.result.ValeResult
import com.tayler.valushopping.utils.successDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductCreateActivity  : BaseActivity(),UiTayCameraManager.CameraControllerListener{
    private val viewModel: DataViewModel by viewModels()
    private lateinit var binding: ActivityProductBinding
    private var managerCamera : UiTayCameraManager? = null
    private var dataProduct = ProductResponse()
    private var fileImage = EMPTY_VALE
    private var type = 0
    private var gender = 0
    private var typeCreate = true
    companion object {
        fun newInstance(context: Context,product : ProductResponse= ProductResponse()) {
            val intent = Intent(context, ProductCreateActivity::class.java)
            intent.putExtra(ProductCreateActivity::class.java.name,product)
            context.startActivity(intent)
        }
    }


    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configInit()
        configAction()
        configChange()
    }

    override fun observeViewModel() {
        viewModel.successProductLiveData.observe(this){
            this.successDialog{
                ValeResult.eventUpdateListProduct.postValue(EventUiTay(true))
                finish()
            }
        }
    }
    private fun loadService(){
        mapperData()
        if (typeCreate){
            viewModel.saveProduct(dataProduct)
        }else{
            viewModel.updateProduct(dataProduct)
        }

    }
    private fun configAction(){
        binding.imgProduct.setOnClickUiTayDelay {
            if (typeCreate)managerCamera?.doCamera("valeImageProduct")
        }
        binding.tbProduct.setOnClickTayBackListener{onToBack()}
        binding.btnSaveProduct.setOnClickTayBtnListener{ loadService()}
        binding.tbProduct.setOnClickTayMenuListener{
            if (!typeCreate){ AddImagesActivity.newInstance(this,dataProduct) }}
    }

    private fun configChange(){
        binding.editUnit.setOnChangeTayEditLayoutListener{ enableBtn()}
        binding.editTotal.setOnChangeTayEditLayoutListener{ enableBtn()}
        binding.editNameProduct.setOnChangeTayEditListener{ enableBtn()}
        binding.editDescriptionProduct.addTextChangedListener{ enableBtn()}
    }

    private fun configInit(){
        dataProduct = intent.uiTayParcelable(ProductCreateActivity::class.java.name)?:ProductResponse()
        managerCamera = UiTayCameraManager(this,"product",this)
        typeCreate = dataProduct.tyFlowCreate()
        binding.tbProduct.uiTayStyleTbIcon = if (typeCreate)
            UITayStyleTbIcon.UI_TAY_ICON_START else UITayStyleTbIcon.UI_TAY_ICON_TWO
        if (!typeCreate)configDataProduct()
        if (!typeCreate)configRg()
        configRgGender()
        configRgType()
    }

    private fun configRg()= with(binding){
        type = dataProduct.type?.toInt()?:0
        gender = dataProduct.gender?.toInt()?:0
        rbFemale.isChecked = dataProduct.gender == "0"
        rbMale.isChecked = dataProduct.gender == "1"
        rbUnisex.isChecked = dataProduct.gender == "2"
        rbImitationJewelry.isChecked = dataProduct.type == "0"
        rbPole.isChecked = dataProduct.type == "1"
        rbPants.isChecked = dataProduct.type == "2"
        rbFootwear.isChecked = dataProduct.type == "3"
        rbSkirt.isChecked = dataProduct.type == "4"
        rbGarments.isChecked = dataProduct.type == "5"
        rbOther.isChecked = dataProduct.type == "6"
        rbAccessories.isChecked = dataProduct.type == "7"
    }

    private fun configDataProduct()= with(binding){
        binding.btnSaveProduct.tayBtnText = "Actualizar"
        uiTayTryCatch {
            imgProduct.uiTayLoadUrl(dataProduct.getLoadImage()) }
        editUnit.uiTayLText= dataProduct.price?: EMPTY_VALE
        editTotal.uiTayLText= dataProduct.priceTwo?: EMPTY_VALE
        editUnit.uiTayLText= dataProduct.price?: EMPTY_VALE
        editNameProduct.uiTayLabelEdit = dataProduct.name?: EMPTY_VALE
        editPhoneProduct.uiTayLabelEdit = dataProduct.phone?: EMPTY_VALE
        editDescriptionProduct.setText(dataProduct.description?: EMPTY_VALE)
        cbState.isChecked = dataProduct.state?:false
        cbPrincipal.isChecked = dataProduct.principal?:false
        btnSaveProduct.tayBtnEnable = true
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
        flagEnable  += if(binding.editUnit.uiTayLText.isNotEmpty()) 0 else 1
        if (typeCreate)flagEnable  += if(fileImage.isNotEmpty()) 0 else 1
        flagEnable  += if(binding.editPhoneProduct.uiTayLabelEdit.uiTayValidatePhoneFormat()) 0 else 1
        binding.btnSaveProduct.tayBtnEnable = flagEnable == 0
    }

    private fun mapperData(){
        dataProduct.name = binding.editNameProduct.uiTayLabelEdit
        dataProduct.description = binding.editDescriptionProduct.text.toString()
        dataProduct.price = binding.editUnit.uiTayLText.uiTayFormatDecimal()
        dataProduct.priceTwo = binding.editTotal.uiTayLText.uiTayFormatDecimal()
        dataProduct.phone = binding.editPhoneProduct.uiTayLabelEdit
        if(typeCreate)dataProduct.img =  fileImage
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