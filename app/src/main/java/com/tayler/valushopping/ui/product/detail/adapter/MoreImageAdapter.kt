package com.tayler.valushopping.ui.product.detail.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.tayler.valushopping.databinding.RowMoreImageBinding
import com.tayler.valushopping.repository.network.model.response.ImageMoreResponse
import com.tayler.valushopping.utils.createBitmapFromView

class MoreImageAdapter(var onClickDelete: ((ImageMoreResponse) -> Unit)? = null,var onClickImage: ((Bitmap) -> Unit)? = null) :
    RecyclerView.Adapter<MoreImageAdapter.AdminViewHolder>() {

    var imageList: List<ImageMoreResponse> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        return AdminViewHolder(
            RowMoreImageBinding.inflate(
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


    inner class AdminViewHolder(private val binding: RowMoreImageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: ImageMoreResponse) {
            binding.image = image
            binding.executePendingBindings()
            binding.ctlImageMore.setOnClickUiTayDelay{
                onClickImage?.invoke(
                    createBitmapFromView(binding.rowMoreImg)
                )
            }
            binding.rowMoreImgDelete.setOnClickUiTayDelay {
                onClickDelete?.invoke(image)
            }
        }
    }
}