package com.tayler.valushopping.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModel
import com.gb.vale.uitaylibrary.utils.showUiTayDialog
import com.tayler.valushopping.component.CrossProgressBarFull
import com.tayler.valushopping.utils.addToBaseNavigation
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            viewModel.errorLiveData.observe(this) { it?.let { showError(it) } }
            viewModel.loadingLiveData.observe(this) { showLoading(it) }
        }
    }

    fun showError(error: Throwable) {
        val mapper = error.mapperError()
        showUiTayDialog(model = UiTayDialogModel(image = mapper.first, title = mapper.second,
            subTitle = mapper.third)){

        }
    }

    fun showLoading(isLoading: Boolean) {
        cyMProgressBar.apply {
            if (isLoading) {
                this?.show()
            } else {
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