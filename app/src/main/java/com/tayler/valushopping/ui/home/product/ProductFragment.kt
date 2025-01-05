package com.tayler.valushopping.ui.home.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.FragmentProductBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.ui.BaseFragment
import com.tayler.valushopping.ui.home.product.adapter.ProductAdapter


class ProductFragment : BaseFragment() {

    private lateinit var binding: FragmentProductBinding
    private var productAdapter = ProductAdapter()


    companion object { fun newInstance() = ProductFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
        binding.productAdapter = productAdapter
        productAdapter.adminList = arrayListOf(ProductResponse(),ProductResponse(),ProductResponse(),ProductResponse(),ProductResponse(),ProductResponse(),ProductResponse(),ProductResponse(),ProductResponse())
        productAdapter.onClickItem = {}
    }

    override fun observeLiveData() {
    }


}