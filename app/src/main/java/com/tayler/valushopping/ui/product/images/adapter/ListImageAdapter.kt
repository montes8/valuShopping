package com.tayler.valushopping.ui.product.images.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.tayler.valushopping.databinding.RowListImageBinding
import com.tayler.valushopping.repository.network.model.response.ImageMoreResponse
import com.tayler.valushopping.utils.createBitmapFromView

class ListImageAdapter(var onClickDelete: ((ImageMoreResponse) -> Unit)? = null, var onClickImage: ((Bitmap) -> Unit)? = null) :
    RecyclerView.Adapter<ListImageAdapter.AdminViewHolder>() {

    var imageList: List<ImageMoreResponse> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        return AdminViewHolder(
            RowListImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size


    inner class AdminViewHolder(private val binding: RowListImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: ImageMoreResponse) {
            binding.image = image
            binding.executePendingBindings()
            binding.ctlListImageMore.setOnClickUiTayDelay{
                onClickImage?.invoke(
                    createBitmapFromView(binding.rowMoreListImg)
                )
            }
            binding.rowMoreImgDelete.setOnClickUiTayDelay {
                onClickDelete?.invoke(image)
            }
        }
    }
}