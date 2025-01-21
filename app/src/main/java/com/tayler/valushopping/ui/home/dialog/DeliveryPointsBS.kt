package com.tayler.valushopping.ui.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.BsDeliveryPointsBinding
import javax.annotation.Nullable


class DeliveryPointsBS : BottomSheetDialogFragment(){

    private lateinit var binding : BsDeliveryPointsBinding
    companion object {
        fun newInstance(): DeliveryPointsBS = DeliveryPointsBS()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgCloseDeliveryPoints.setOnClickUiTayDelay{ dismiss()}
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BsDeliveryPointsBinding.inflate(inflater,container,false)
        return binding.root
    }

}