package com.tayler.valushopping.ui.splash

import android.annotation.SuppressLint
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.utils.uiTayHandler
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivitySplashBinding
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.home.HomeActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configAnimation()
    }

    private fun configAnimation(){
        val ani = AnimationUtils.loadAnimation(this, R.anim.animation_top)
        val ani2 = AnimationUtils.loadAnimation(this, R.anim.animation_botton)
        binding.ltSplash.animation=ani
        binding.ctlBottom.animation=ani2
        uiTayHandler(2000) { HomeActivity.newInstance(this) }
    }
    override fun observeViewModel() {
        //not implement
    }

    override fun getViewModel(): BaseViewModel? = null
}