package com.tayler.valushopping.ui.home

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityHomeBinding
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this    }

    override fun setUpView() {
    }

    override fun observeViewModel() {
    }

    override fun getViewModel(): BaseViewModel? = null
}