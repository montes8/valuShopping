package com.tayler.valushopping.ui.home.product.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.tayler.valushopping.databinding.RowOtherBinding
import com.tayler.valushopping.repository.network.model.ProductResponse

class ProductAdapter(var onClickItem: ((ProductResponse) -> Unit)? = null) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var productList: List<ProductResponse> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
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
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductViewHolder(private val binding: RowOtherBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: ProductResponse) {
            binding.product = product
            binding.executePendingBindings()
            binding.ctlAdmin.setOnClickUiTayDelay{onClickItem?.invoke(product) }
        }
    }
}