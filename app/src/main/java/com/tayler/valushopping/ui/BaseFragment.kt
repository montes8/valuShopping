package com.tayler.valushopping.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun getMainView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View

    abstract fun setUpView()
    abstract fun observeLiveData()
    open fun getViewModel(): BaseViewModel? = null

    open fun onDetectedBackFragment(): (() -> Unit)? = null

    open fun isBackDisable(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = getMainView(inflater, container)
        setUpView()
        observeMainViewModel()
        if (isBackDisable())requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeLiveData()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeMainViewModel() {
        if (activity is BaseActivity) {
            val context = activity as BaseActivity
            getViewModel()?.let { viewModel ->
                viewModel.errorLiveData.observe(context) {
                    it?.let {
                        (activity as BaseActivity).showError(it)
                    }
                }
                viewModel.loadingLiveData.observe(context) {
                    (activity as BaseActivity).showLoading(
                        it
                    )
                }
            }
        }
    }

    fun <T> setDataShared(value: T) {
        (activity as BaseActivity).setDataShared(value)
    }

    fun getDataShared() = (activity as BaseActivity).dataSharedVale

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onDetectedBackFragment()
            }
        }

    fun addToNavigationFragment(fragment: BaseFragment) {
        (activity as BaseActivity).addToNavigation(fragment)
    }

    fun onBackFragment() {
        (activity as BaseActivity).onToBack()
    }
}