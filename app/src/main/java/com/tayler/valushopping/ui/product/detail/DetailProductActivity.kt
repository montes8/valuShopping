package com.tayler.valushopping.ui.product.detail

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.utils.uiTayParcelable
import com.gb.vale.uitaylibrary.utils.uiTayShowToast
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityDetailProductBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.getUriFromConstancyOneView
import com.tayler.valushopping.utils.openWhatsApp
import com.tayler.valushopping.utils.shareImage

class DetailProductActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private var product : ProductResponse? = null

    companion object {
        fun newInstance(context: Context,product : ProductResponse) {
            val intent = Intent(context, DetailProductActivity::class.java)
            intent.putExtra(DetailProductActivity::class.java.name,product)
            context.startActivity(intent)
        }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_product)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        product = intent.uiTayParcelable(DetailProductActivity::class.java.name)
        product?.let { binding.product = it }
        binding.btnConsult.setOnClickTayBtnListener {
            if (product?.state== true){
                openWhatsApp(product?.phone?: EMPTY_VALE,"Hola me gustaria consultar : ${product?.name}")
            }else{
                uiTayShowToast("Producto no disponible")
            }

        }

        binding.btnShared.setOnClickTayBtnListener {
            if (product?.state== true){
                sharedProduct()
            }else{
                uiTayShowToast("Producto no disponible")
            }

        }

        binding.tbDetailProduct.setOnClickTayBackListener{
            finish()
        }
    }

    private fun sharedProduct() {
        val uri = getUriFromConstancyOneView(
            this, binding.root, binding.ctnGeneralProduct
        )
        shareImage(uri, this)
    }

    override fun observeViewModel() {}
}