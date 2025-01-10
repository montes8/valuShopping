package com.tayler.valushopping.ui.home.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.FragmentOtherBinding
import com.tayler.valushopping.ui.BaseFragment
import com.tayler.valushopping.ui.home.other.adapter.OtherAdapter
import com.tayler.valushopping.utils.JSON_ITEM_OTHER
import com.tayler.valushopping.utils.getData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherFragment : BaseFragment() {

    private lateinit var binding: FragmentOtherBinding
    private var otherAdapter = OtherAdapter()

    companion object { fun newInstance() = OtherFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_other, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
        binding.otherAdapter = otherAdapter
        otherAdapter.adminList = getData(requireContext(), JSON_ITEM_OTHER)
        otherAdapter.onClickAdmin = {}
    }

    override fun observeLiveData() {
    }


}