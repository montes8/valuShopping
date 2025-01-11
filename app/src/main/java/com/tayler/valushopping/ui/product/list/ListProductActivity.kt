package com.tayler.valushopping.ui.product.list

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityListProductBinding
import com.tayler.valushopping.ui.BaseActivity

class ListProductActivity : BaseActivity() {

    private lateinit var binding: ActivityListProductBinding

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, ListProductActivity::class.java))
        }
    }
    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_product)
        binding.lifecycleOwner = this    }

    override fun setUpView() {
    }

    override fun observeViewModel() {
    }

}