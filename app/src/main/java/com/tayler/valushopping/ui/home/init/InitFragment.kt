package com.tayler.valushopping.ui.home.init

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.FragmentAdminBinding
import com.tayler.valushopping.ui.BaseFragment


class InitFragment : BaseFragment() {

    private lateinit var binding: FragmentAdminBinding

    companion object { fun newInstance() = InitFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_admin, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
    }

    override fun observeLiveData() {
    }


}