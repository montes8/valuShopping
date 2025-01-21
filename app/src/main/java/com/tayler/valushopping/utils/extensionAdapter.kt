package com.tayler.valushopping.utils

import android.graphics.BitmapFactory
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gb.vale.uitaylibrary.utils.converterCircle
import com.gb.vale.uitaylibrary.utils.uiTayBgBorder
import com.gb.vale.uitaylibrary.utils.uiTayLoadUrl
import com.gb.vale.uitaylibrary.utils.uiTayTryCatch
import com.tayler.valushopping.R

@BindingAdapter("srcDrawableString")
fun setImageDrawableString(imageView: ImageView, nameImage: String?) {
    uiTayTryCatch {
        nameImage?.let { imageView.setImageDrawable(setImageString(nameImage,imageView.context)) }
    }
}

@BindingAdapter("srcDrawableStringUrl")
fun setImageDrawableStringUrl(imageView: ImageView, nameImage: String?) {
    uiTayTryCatch {
        nameImage?.let { imageView.uiTayLoadUrl(it)}
    }
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


@BindingAdapter("mapperStateProduct")
fun setMapperStateProduct(textView: TextView, state: Boolean?) {
    state?.let {
            textView.uiTayBgBorder(color = if(it) R.color.green else R.color.red, radius = com.gb.vale.uitaylibrary.R.dimen.dim_tay_8)
            textView.text = textView.context.getString(if(it) R.string.text_available else R.string.text_exhausted)
    }
}