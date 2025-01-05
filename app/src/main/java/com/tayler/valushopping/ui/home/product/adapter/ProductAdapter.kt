package com.tayler.valushopping.ui.home.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.tayler.valushopping.R
import com.tayler.valushopping.databinding.RowOtherBinding
import com.tayler.valushopping.repository.network.model.ProductResponse

class ProductAdapter(var onClickItem: ((ProductResponse) -> Unit)? = null) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var adminList: List<ProductResponse> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            RowOtherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(adminList[position])
    }

    override fun getItemCount(): Int = adminList.size

    inner class ProductViewHolder(private val binding: RowOtherBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(other: ProductResponse) {
            binding.executePendingBindings()
            binding.rowProductImg.setImageResource(R.drawable.ic_logo_other_product)
            binding.ctlAdmin.setOnClickUiTayDelay{onClickItem?.invoke(other) }
        }
    }
}