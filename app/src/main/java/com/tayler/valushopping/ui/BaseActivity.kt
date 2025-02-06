package com.tayler.valushopping.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.tayler.valushopping.component.CrossProgressBarFull
import com.tayler.valushopping.utils.addToBaseNavigation
import com.tayler.valushopping.utils.errorDialog
import com.tayler.valushopping.utils.mapperError
import com.tayler.valushopping.utils.onToBaseBack

abstract class BaseActivity : AppCompatActivity() {

    var dataSharedVale: Any? = null
    var activateBack = true
    abstract fun getMainView()
    abstract fun setUpView()
    abstract fun observeViewModel()
    open fun getBackFinish(): Boolean = true
    open fun getViewModel(): BaseViewModel? = null

    private var cyMProgressBar: CrossProgressBarFull? = null
    private var currentFragment: BaseFragment? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        getMainView()
        cyMProgressBar =
            CrossProgressBarFull(this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        observeMainViewModel()
        setUpView()

    }

    private fun observeMainViewModel() {
        this.observeViewModel()
        getViewModel()?.let { viewModel ->
            viewModel.errorLiveData.observe(this) { it?.let {
                Log.d("servicess","baseerrorLiveData")
                showError(it) } }
            viewModel.loadingLiveData.observe(this) {
                Log.d("servicess","baseloadingLiveData")
                showLoading(it) }
        }
    }

    fun showError(error: Throwable) {
        val mapper = error.mapperError()
        errorDialog(img =  mapper.first, title = mapper.second,
            subTitle = mapper.third){
            //not implement
        }
    }

    fun showLoading(isLoading: Boolean) {
        cyMProgressBar.apply {
            if (isLoading) {
                Log.d("servicess","showLoading")
                this?.show()
            } else {
                Log.d("servicess","hideLoading")
                this?.dismiss()
            }
        }
    }


    fun addToNavigation(fragment: BaseFragment) {
        addToBaseNavigation(fragment, currentFragment) {
            currentFragment = it
            currentFragment?.let { c -> onNextDetected(c) }
        }
    }

    fun onToBack() {
        currentFragment?.let { onBackDetected(it) }
        onToBaseBack(currentFragment, finish = getBackFinish()) { currentFragment = it }
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (activateBack)onToBack()
            }
        }

    open fun onBackDetected(currentFragment: BaseFragment): Boolean = true

    open fun onNextDetected(currentFragment: BaseFragment): Boolean = true

    fun <T> setDataShared(value: T) {
        dataSharedVale = value
    }

    fun getDataShared() = dataSharedVale

}