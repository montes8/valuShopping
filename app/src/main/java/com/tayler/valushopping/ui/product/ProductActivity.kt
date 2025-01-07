package com.tayler.valushopping.ui.product

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityProductBinding
import com.tayler.valushopping.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : BaseActivity() {

    private lateinit var binding: ActivityProductBinding

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, ProductActivity::class.java))
        }
    }
    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        binding.lifecycleOwner = this    }

    override fun setUpView() {
    }

    override fun observeViewModel() {
    }
}