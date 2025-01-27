package com.tayler.valushopping.ui.product.list

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModel
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModelCustom
import com.gb.vale.uitaylibrary.swipe.UiTayCardSwipeButton
import com.gb.vale.uitaylibrary.utils.EventUiTayObserver
import com.gb.vale.uitaylibrary.utils.showUiTayDialog
import com.gb.vale.uitaylibrary.utils.uiTayAddSwipe
import com.gb.vale.uitaylibrary.utils.uiTayVisibilityDuo
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityListProductBinding
import com.tayler.valushopping.entity.singleton.AppDataVale
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.home.product.adapter.ProductListAdapter
import com.tayler.valushopping.ui.product.DataViewModel
import com.tayler.valushopping.ui.product.add.ProductActivity
import com.tayler.valushopping.ui.product.update.UpdateProductActivity
import com.tayler.valushopping.utils.EMPTY_VALE
import com.tayler.valushopping.utils.result.ValeResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListProductActivity : BaseActivity() {
    private val viewModel: DataViewModel by viewModels()
    private lateinit var binding: ActivityListProductBinding
    private var productListAdapter = ProductListAdapter()
    private var listProduct : List<ProductResponse> = ArrayList()

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
        binding.tbLitProductAdmin.setOnClickTayMenuListener{ProductActivity.newInstance(this)}
        binding.productListAdapter = productListAdapter
        binding.rvProductListAdmin.uiTayAddSwipe(this){
            it.add(UiTayCardSwipeButton(this, imageResId = R.drawable.ic_delete){position ->
                onClickDelete(position)
            })
        }
        loadService()
    }

    private fun loadService(){
        viewModel.loadProduct(true,AppDataVale.user.rol != "ADMIN")
    }

    private fun onClickDelete(position:Int){
        showUiTayDialog(model = UiTayDialogModel(title = getString(R.string.text_title_delete),
            subTitle = getString(R.string.sub_text_title_delete),
            styleCustom =
            UiTayDialogModelCustom(btnAcceptSolidColor = R.color.red, btnAcceptStrokeColor = R.color.red)
        )
        ){
           if (it){
               viewModel.loadDeleteProduct(listProduct[position].uid?: EMPTY_VALE)
           }
        }
    }
    override fun observeViewModel() {
        viewModel.successLoadProductLiveData.observe(this){
            configList(it)
        }

        viewModel.shimmerLiveData.observe(this) {
            binding.shimmerProductAdmin.uiTayVisibilityDuo(it, binding.rvProductListAdmin)
        }

        viewModel.successDeleteLiveData.observe(this){
            loadService()
        }

        ValeResult.eventUpdateListProduct.observe(this,EventUiTayObserver{
            loadService()
           })

    }

    private fun configList(list : List<ProductResponse>){
        listProduct = list
        binding.rvProductListAdmin.uiTayVisibilityDuo(list.isNotEmpty(),binding.ctnListEmptyProductAdmin)
        productListAdapter.productList = list
        productListAdapter.onClickItem = {
            UpdateProductActivity.newInstance(this,it)
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}