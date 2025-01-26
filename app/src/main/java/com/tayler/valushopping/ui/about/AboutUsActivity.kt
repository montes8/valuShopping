package com.tayler.valushopping.ui.about

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.ActivityAboutUsBinding
import com.tayler.valushopping.ui.BaseActivity

class AboutUsActivity : BaseActivity() {

    private lateinit var binding: ActivityAboutUsBinding

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, AboutUsActivity::class.java))
        }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        binding.tbAbout.setOnClickTayBackListener{finish()}
    }

    override fun observeViewModel() {
    }

}