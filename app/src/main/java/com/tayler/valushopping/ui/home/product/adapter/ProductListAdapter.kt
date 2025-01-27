package com.tayler.valushopping.ui.home.product.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.gb.vale.uitaylibrary.utils.uiTayVisibility
import com.tayler.valushopping.databinding.RowProductBinding
import com.tayler.valushopping.repository.network.model.ProductResponse
import com.tayler.valushopping.utils.createBitmapFromView

class ProductListAdapter(var state : Boolean = true,var onClickImage: ((Bitmap) -> Unit)? = null,var onClickItem: ((ProductResponse) -> Unit)? = null) :
    RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    var productList: List<ProductResponse> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(
            RowProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductListViewHolder(private val binding: RowProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: ProductResponse) {
            binding.product = product
            binding.executePendingBindings()
            binding.lnCtnRowProduct.setOnClickUiTayDelay{onClickItem?.invoke(product) }
            binding.rowTextStateProduct.uiTayVisibility(state)
            binding.rowImgProductList.setOnClickUiTayDelay { onClickImage?.invoke(
                createBitmapFromView(binding.rowImgProductList)
            )
            }
        }
    }
}