package com.tayler.valushopping.ui.product.detail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.gb.vale.uitaylibrary.utils.uiTayBgBorder

import com.gb.vale.uitaylibrary.utils.uiTayParcelable
import com.gb.vale.uitaylibrary.utils.uiTayShowToast
import com.gb.vale.uitaylibrary.utils.uiTayVisibility
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityDetailProductBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.product.DataViewModel
import com.tayler.valushopping.ui.product.detail.adapter.MoreImageAdapter
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.createBitmapFromView
import com.tayler.valushopping.utils.dialogZoom
import com.tayler.valushopping.utils.openWhatsApp
import com.tayler.valushopping.utils.sharedImageView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private val viewModel: DataViewModel by viewModels()
    private var product : ProductResponse? = null
    private val adapterImage = MoreImageAdapter()

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
        binding.adapterMore = adapterImage
        binding.lnInfoDetail.uiTayBgBorder(color = R.color.red_50, radius = R.dimen.size_8,
            )
        adapterImage.onClickImage = {
            dialogZoomDetail(it)
        }
        viewModel.loadMoreImageProduct(product?.uid?: EMPTY_VALE)
        binding.btnConsult.setOnClickTayBtnListener {
            if (product?.state== true){
                openWhatsApp(product?.phone?: EMPTY_VALE,"Hola me gustaria adquirir el producto ${product?.name} aun esta disponible : ")
            }else{
                uiTayShowToast("Producto no disponible")
            }
        }

        binding.btnShared.setOnClickTayBtnListener {
            if (product?.state== true){
                sharedImageView(binding.lnHeaderProduct)
            }else{
                uiTayShowToast("Producto no disponible")
            }
        }

        binding.tbDetailProduct.setOnClickTayBackListener{
            finish()
        }

        binding.imageDetailProduct.setOnClickUiTayDelay {
            dialogZoomDetail(createBitmapFromView(binding.imageDetailProduct))
        }
    }


    private  fun dialogZoomDetail(image : Bitmap){
        dialogZoom(image)
    }
    override fun observeViewModel() {
        viewModel.successProductImageLiveData.observe(this){
            adapterImage.imageList = it
            binding.rvMoreImage.uiTayVisibility(it.isNotEmpty())
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel


}