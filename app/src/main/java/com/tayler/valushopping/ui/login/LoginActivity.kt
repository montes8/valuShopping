package com.tayler.valushopping.ui.login

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityLoginBinding
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    companion object {
        fun newInstance(context: Context) {
           context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
    }

    override fun observeViewModel() {
    }

    override fun getViewModel(): BaseViewModel? = null
}