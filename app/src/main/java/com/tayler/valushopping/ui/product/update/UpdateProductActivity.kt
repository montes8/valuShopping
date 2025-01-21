package com.tayler.valushopping.ui.product.update

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.utils.EventUiTay
import com.gb.vale.uitaylibrary.utils.uiTayFormatDecimal
import com.gb.vale.uitaylibrary.utils.uiTayLoadUrl
import com.gb.vale.uitaylibrary.utils.uiTayParcelable
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityProductBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.product.DataViewModel
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.TYPE_CLOTHES
import com.tayler.valushopping.utils.TYPE_OTHER
import com.tayler.valushopping.utils.result.ValeResult
import com.tayler.valushopping.utils.successDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProductActivity : BaseActivity() {
    private val viewModel: DataViewModel by viewModels()
    private lateinit var binding: ActivityProductBinding
    private var product  = ProductResponse()
    private var type = 0
    private var gender = 0
    companion object {
        fun newInstance(context: Context,product : ProductResponse) {
            val intent = Intent(context, UpdateProductActivity::class.java)
            intent.putExtra(UpdateProductActivity::class.java.name,product)
            context.startActivity(intent)
        }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        binding.lifecycleOwner = this     }

    override fun setUpView() {
        product = intent.uiTayParcelable(UpdateProductActivity::class.java.name)?:ProductResponse()
        configDataProduct()
        configAction()
        configRg()
    }

    override fun observeViewModel() {
        viewModel.successUpdateProductLiveData.observe(this){
            this.successDialog{
                ValeResult.eventUpdateListProduct.postValue(EventUiTay(true))
                finish()
            }
        }
    }


    private fun configDataProduct()= with(binding){
        imgProduct.uiTayLoadUrl(product.getLoadImage())
        editUnit.uiTayLText= product.price?: EMPTY_VALE
        editTotal.uiTayLText= product.priceTwo?: EMPTY_VALE
        editUnit.uiTayLText= product.price?: EMPTY_VALE
        editNameProduct.uiTayLabelEdit = product.name?: EMPTY_VALE
        editPhoneProduct.uiTayLabelEdit = product.phone?: EMPTY_VALE
        editDescriptionProduct.setText(product.description?: EMPTY_VALE)
        cbState.isChecked = product.state?:false
        cbPrincipal.isChecked = product.principal?:false
        btnSaveProduct.tayBtnText = "Actualizar producto"
        btnSaveProduct.tayBtnEnable = true
    }

    private fun configAction(){
        binding.tbProduct.setOnClickTayBackListener{finish()}
        binding.btnSaveProduct.setOnClickTayBtnListener{
            mapperData()
            viewModel.updateProduct(product)
        }
        configRgGender()
        configRgType()
    }

    private fun configRg()= with(binding){
        type = product.type?.toInt()?:0
        gender = product.gender?.toInt()?:0
        rbFemale.isChecked = product.gender == "0"
        rbMale.isChecked = product.gender == "1"
        rbUnisex.isChecked = product.gender == "2"
        rbImitationJewelry.isChecked = product.type == "0"
        rbPole.isChecked = product.type == "1"
        rbPants.isChecked = product.type == "2"
        rbFootwear.isChecked = product.type == "3"
        rbSkirt.isChecked = product.type == "4"
        rbGarments.isChecked = product.type == "5"
        rbOther.isChecked = product.type == "6"
        rbAccessories.isChecked = product.type == "7"
    }

    private fun configRgGender()= with(binding){
        rgGender.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rbFemale->{ gender = 0 }
                R.id.rbMale->{ gender = 1 }
                R.id.rbUnisex->{ gender = 2 }
            }
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
        }
    }

    private fun mapperData(){
        product.name = binding.editNameProduct.uiTayLabelEdit
        product.description = binding.editDescriptionProduct.text.toString()
        product.price = binding.editUnit.uiTayLText.uiTayFormatDecimal()
        product.priceTwo = binding.editTotal.uiTayLText.uiTayFormatDecimal()
        product.phone = binding.editPhoneProduct.uiTayLabelEdit
        product.state = binding.cbState.isChecked
        product.type = type.toString()
        product.gender = gender.toString()
        product.principal = binding.cbPrincipal.isChecked
        product.category = if(type != 5) TYPE_CLOTHES else TYPE_OTHER
    }

    override fun getViewModel(): BaseViewModel = viewModel

}