package com.tayler.valushopping.ui.home.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.gb.vale.uitaylibrary.utils.callPhoneIntent
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.FragmentAdminBinding
import com.tayler.valushopping.entity.ItemModel
import com.tayler.valushopping.ui.BaseFragment
import com.tayler.valushopping.ui.home.admin.adapter.AdminAdapter
import com.tayler.valushopping.ui.login.LoginActivity
import com.tayler.valushopping.ui.param.ParamActivity
import com.tayler.valushopping.ui.product.add.ProductActivity
import com.tayler.valushopping.ui.product.list.ListProductActivity
import com.tayler.valushopping.utils.JSON_ITEM_ADMIN
import com.tayler.valushopping.utils.getData
import com.tayler.valushopping.utils.openWhatsApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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
        adapterAdmin.adminList = getData(requireContext(), JSON_ITEM_ADMIN)
        adapterAdmin.onClickAdmin = {configOnClickAdapter(it)}
    }

    private fun configOnClickAdapter(model : ItemModel){
        when(model.id){
            1 -> {
                ProductActivity.newInstance(requireContext())

            }
            2 ->{
                ParamActivity.newInstance(requireContext())

            }
            3 -> {
                ListProductActivity.newInstance(requireContext())
            }
            4 ->{
                requireContext().openWhatsApp("935815994","Hola necesito ayuda, me comunico de app Valu shopping")
            }
            5 ->{
                callPhoneIntent(requireContext(),"935815994")

            }
            6 ->{
                LoginActivity.newInstance(requireContext())

            }
            else->{
                //recomendacion

            }
        }
    }
    override fun observeLiveData() {
    }


}