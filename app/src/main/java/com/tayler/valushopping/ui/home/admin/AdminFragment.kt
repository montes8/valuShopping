package com.tayler.valushopping.ui.home.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.FragmentAdminBinding
import com.tayler.valushopping.entity.ItemModel
import com.tayler.valushopping.ui.BaseFragment
import com.tayler.valushopping.ui.home.admin.adapter.AdminAdapter
import com.tayler.valushopping.ui.login.LoginActivity
import com.tayler.valushopping.utils.JSON_ITEM
import com.tayler.valushopping.utils.getData


class AdminFragment : BaseFragment() {

    private lateinit var binding: FragmentAdminBinding
    private var adapterAdmin = AdminAdapter()

    companion object { fun newInstance() = AdminFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_admin, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
        binding.adapterAdmin = adapterAdmin
        adapterAdmin.adminList = getData(requireContext(), JSON_ITEM)
        adapterAdmin.onClickAdmin = {configOnClickAdapter(it)}
    }

    private fun configOnClickAdapter(model : ItemModel){
        when(model.id){
            1 -> {
                //productos

            }
            2 ->{
                //parametros

            }
            3,6 -> {
                //whatsapp
            }
            4,7 ->{
                //contactanos
            }
            5 ->{
                LoginActivity.newInstance(requireContext())

            }
            else->{
                //ayuda

            }
        }
    }
    override fun observeLiveData() {
    }


}