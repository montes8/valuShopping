package com.tayler.valushopping.ui.product.detail

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityDetailProductBinding
import com.tayler.valushopping.ui.BaseActivity

class DetailProductActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, DetailProductActivity::class.java))
        }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_product)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
    }

    override fun observeViewModel() {
    }
}