package com.tayler.valushopping.ui.product.update

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.utils.uiTayLoadUrl
import com.gb.vale.uitaylibrary.utils.uiTayParcelable
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityProductBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.product.detail.DetailProductActivity
import com.tayler.valushopping.utils.EMPTY_VALE

class UpdateProductActivity : BaseActivity() {

    private lateinit var binding: ActivityProductBinding
    private var product : ProductResponse? = null

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
        product = intent.uiTayParcelable(DetailProductActivity::class.java.name)
        configDataProduct()
    }

    private fun configDataProduct()= with(binding){
        binding.tbProduct.setOnClickTayBackListener{finish()}
        imgProduct.uiTayLoadUrl(product?.img?: EMPTY_VALE)
        editUnit.uiTayLText= product?.price?: EMPTY_VALE
        editTotal.uiTayLText= product?.priceTwo?: EMPTY_VALE
        editUnit.uiTayLText= product?.price?: EMPTY_VALE
        editNameProduct.uiTayLabelEdit = product?.name?: EMPTY_VALE
        editPhoneProduct.uiTayLabelEdit = product?.phone?: EMPTY_VALE
        editDescriptionProduct.setText(product?.description?: EMPTY_VALE)
        cbState.isChecked = product?.state?:false
        cbPrincipal.isChecked = product?.principal?:false
        btnSaveProduct.tayBtnText = "Actualizar producto"
    }

    override fun observeViewModel() {
    }

}