package com.tayler.valushopping

import android.annotation.SuppressLint
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.databinding.ActivitySplashBinding

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
    }
    override fun observeViewModel() {
        //not implement
    }

    override fun getViewModel(): BaseViewModel? = null
}