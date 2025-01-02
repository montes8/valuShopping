package com.tayler.valushopping.ui.home.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.tayler.valushopping.BR
import com.tayler.valushopping.databinding.RowAdminBinding
import com.tayler.valushopping.entity.ItemModel

class AdminAdapter(var onClickAdmin: ((ItemModel) -> Unit)? = null) :
    RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    var adminList: List<ItemModel> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        return AdminViewHolder(
            RowAdminBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        holder.bind(adminList[position])
    }

    override fun getItemCount(): Int = adminList.size



    inner class AdminViewHolder(private val binding: RowAdminBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(admin: ItemModel) {
            binding.setVariable(BR.admin, admin)
            binding.executePendingBindings()
            binding.ctlAdmin.setOnClickUiTayDelay{onClickAdmin?.invoke(admin) }
        }
    }
}