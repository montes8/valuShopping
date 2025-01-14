package com.tayler.valushopping.utils

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.gb.vale.uitaylibrary.utils.converterCircle
import com.gb.vale.uitaylibrary.utils.uiTayTryCatch

@BindingAdapter("srcDrawableString")
fun setImageDrawableString(imageView: ImageView, nameImage: String?) {
    nameImage?.let { imageView.setImageDrawable(setImageString(nameImage,imageView.context)) }
}

@BindingAdapter("fileDrawableString")
fun setFileDrawableString(imageView: ImageView, nameImage: String?) {
    nameImage?.let {
        if (it.isNotEmpty()){
            uiTayTryCatch { imageView.setImageBitmap(BitmapFactory.decodeFile(it))}
        }
    }
}

@BindingAdapter("fileDrawableStringCircle")
fun setFileDrawableStringCircle(imageView: ImageView, nameImage: String?) {
    nameImage?.let {
        if (it.isNotEmpty()){
            val image  = BitmapFactory.decodeFile(it)
            uiTayTryCatch { imageView.setImageBitmap(image.converterCircle())}
        }
    }
}