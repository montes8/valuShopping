package com.tayler.valushopping.ui.home.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.gb.vale.uitaylibrary.utils.uiTayGone
import com.gb.vale.uitaylibrary.utils.uiTayVisibilityDuo
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.FragmentProductBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.ui.BaseFragment
import com.tayler.valushopping.ui.home.HomeActivity
import com.tayler.valushopping.ui.home.product.adapter.ProductListAdapter
import com.tayler.valushopping.ui.product.DataViewModel
import com.tayler.valushopping.ui.product.detail.DetailProductActivity
import com.tayler.valushopping.utils.dialogZoom
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductFragment : BaseFragment() {
    private val viewModel: DataViewModel by viewModels()
    private lateinit var binding: FragmentProductBinding
    private var productListAdapter = ProductListAdapter()
    private var flagView = true

    companion object { fun newInstance() = ProductFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
        configInit()

    }
    private fun configInit(){
        binding.productListAdapter = productListAdapter
        loadService()
         binding.refreshList.setOnRefreshListener {
             binding.refreshList.isRefreshing = true
             loadService()
         }
    }

    private fun loadService(){
        flagView = true
        binding.rvProductList.uiTayGone()
        viewModel.loadProduct()
    }

    private fun configList(list : List<ProductResponse>){
        binding.refreshList.isRefreshing = false
        binding.rvProductList.uiTayVisibilityDuo(list.isNotEmpty(),binding.ctnListEmpty)
        productListAdapter.productList = list
        productListAdapter.onClickItem = {
            DetailProductActivity.newInstance(requireContext(),it)
        }
        productListAdapter.onClickImage = {
            (activity as HomeActivity).dialogZoom(it)
       }
    }

    override fun observeLiveData() {
        viewModel.successLoadProductLiveData.observe(this){
            configList(it)
        }

        viewModel.shimmerLiveData.observe(this) {
            binding.shimmerProduct.uiTayVisibilityDuo(it, binding.ctnListProduct)
        }
    }
}