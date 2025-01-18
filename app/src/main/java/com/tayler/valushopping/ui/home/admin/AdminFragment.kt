package com.tayler.valushopping.ui.home.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.gb.vale.uitaylibrary.utils.callPhoneIntent
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.FragmentAdminBinding
import com.tayler.valushopping.entity.ItemModel
import com.tayler.valushopping.ui.BaseFragment
import com.tayler.valushopping.ui.home.admin.adapter.AdminAdapter
import com.tayler.valushopping.ui.login.LoginActivity
import com.tayler.valushopping.ui.login.UserViewModel
import com.tayler.valushopping.ui.param.ParamActivity
import com.tayler.valushopping.ui.product.add.ProductActivity
import com.tayler.valushopping.ui.product.list.ListProductActivity
import com.tayler.valushopping.utils.JSON_ITEM
import com.tayler.valushopping.utils.JSON_ITEM_ADMIN
import com.tayler.valushopping.utils.getData
import com.tayler.valushopping.utils.openWhatsApp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdminFragment : BaseFragment() {
    private val viewModel: UserViewModel by viewModels()
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
        val flagSession= viewModel.loadSession()
        adapterAdmin.adminList = getData(requireContext(),if(flagSession) JSON_ITEM_ADMIN else JSON_ITEM)
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
                requireContext().openWhatsApp("935815994","Hola deseo que ser parte de valu shooping")
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