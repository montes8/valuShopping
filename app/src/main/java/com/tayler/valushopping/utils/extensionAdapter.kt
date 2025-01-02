package com.tayler.valushopping.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("srcDrawableString")
fun setImageDrawableString(imageView: ImageView, nameImage: String?) {
    nameImage?.let { imageView.setImageDrawable(setImageString(nameImage,imageView.context)) }
}