package com.tayler.valushopping.ui.product.list

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModel
import com.gb.vale.uitaylibrary.swipe.UiTayCardSwipeButton
import com.gb.vale.uitaylibrary.utils.showUiTayDialog
import com.gb.vale.uitaylibrary.utils.uiTayAddSwipe
import com.gb.vale.uitaylibrary.utils.uiTayVisibilityDuo
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityListProductBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.home.product.adapter.ProductListAdapter
import com.tayler.valushopping.ui.product.DataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListProductActivity : BaseActivity() {
    private val viewModel: DataViewModel by viewModels()
    private lateinit var binding: ActivityListProductBinding
    private var productListAdapter = ProductListAdapter()

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, ListProductActivity::class.java))
        }
    }
    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_product)
        binding.lifecycleOwner = this    }

    override fun setUpView() {
        configInit()
    }

    private fun configInit(){
        binding.tbLitProductAdmin.setOnClickTayBackListener{finish()}
        binding.productListAdapter = productListAdapter
        binding.rvProductListAdmin.uiTayAddSwipe(this){
            it.add(UiTayCardSwipeButton(this, imageResId = R.drawable.ic_delete){
                onClickDelete()
            })
        }
        loadService()
    }

    private fun loadService(){
        viewModel.loadProduct()
    }

    private fun onClickDelete(){
        showUiTayDialog(model = UiTayDialogModel(title = "Eliminar producto",
            subTitle = "Estas seguro que deseas eliminar el producto")
        ){

        }
    }
    override fun observeViewModel() {
        viewModel.successLoadProductLiveData.observe(this){
            configList(it)
        }

        viewModel.shimmerLiveData.observe(this) {
            binding.shimmerProductAdmin.uiTayVisibilityDuo(it, binding.rvProductListAdmin)
        }
    }

    private fun configList(list : List<ProductResponse>){
        productListAdapter.productList = list
        productListAdapter.onClickItem = {
        }
    }

}