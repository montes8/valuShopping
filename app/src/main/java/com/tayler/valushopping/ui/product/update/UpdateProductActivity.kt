package com.tayler.valushopping.ui.product.update

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityUpdateProductBinding
import com.tayler.valushopping.ui.BaseActivity

class UpdateProductActivity : BaseActivity() {

    private lateinit var binding: ActivityUpdateProductBinding

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, UpdateProductActivity::class.java))
        }
    }
    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_product)
        binding.lifecycleOwner = this     }

    override fun setUpView() {
    }

    override fun observeViewModel() {
    }

}