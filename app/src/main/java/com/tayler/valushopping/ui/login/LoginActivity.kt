package com.tayler.valushopping.ui.login

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.utils.uiTayShowToast
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityLoginBinding
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private val viewModel: UserViewModel by viewModels()
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
        binding.editEmail.setOnChangeTayEditLayoutListener{ enableBtn() }
        binding.editPassword.setOnChangeTayEditLayoutListener{ enableBtn()}
        binding.btnLogin.setOnClickTayBtnListener{
            viewModel.login(binding.editEmail.uiTayLText,binding.editPassword.uiTayLText)
        }
    }

    private fun enableBtn(){
        binding.btnLogin.tayBtnEnable = binding.editEmail.uiTayLText.isNotEmpty() && binding.editPassword.uiTayLText.isNotEmpty()
    }

    override fun observeViewModel() {
        viewModel.successLoginLiveData.observe(this){
            if (it){
                HomeActivity.newInstance(this)
            }else{
                uiTayShowToast(getString(R.string.error_login))
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}