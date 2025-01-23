package com.tayler.valushopping.ui.splash

import android.annotation.SuppressLint
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.utils.uiTayHandler
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivitySplashBinding
import com.tayler.valushopping.entity.singleton.AppDataVale
import com.tayler.valushopping.ui.AppViewModel
import com.tayler.valushopping.ui.BaseActivity
import com.tayler.valushopping.ui.BaseViewModel
import com.tayler.valushopping.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val viewModel: AppViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configAnimation()
    }

    private fun configAnimation(){
        val aniTop = AnimationUtils.loadAnimation(this, R.anim.animation_top)
        val aniBottom = AnimationUtils.loadAnimation(this, R.anim.animation_botton)
        binding.ctlBottom.animation = aniBottom
        binding.imgBagB.animation = aniBottom
        binding.imgBagBTwo.animation = aniBottom
        binding.imgBag.animation = aniTop
        binding.imgBagTwo.animation = aniTop
        viewModel.loadParam()
    }
    override fun observeViewModel() {
        viewModel.successParamLiveData.observe(this){
            AppDataVale.paramData = it
            uiTayHandler(2000) { HomeActivity.newInstance(this) }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}