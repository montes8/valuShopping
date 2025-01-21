package com.tayler.valushopping.utils

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModel
import com.gb.vale.uitaylibrary.utils.converterCircle
import com.gb.vale.uitaylibrary.utils.showUiTayDialog
import com.gb.vale.uitaylibrary.utils.uiTayTryCatch
import com.tayler.valushopping.R

fun AppCompatActivity.successDialog(title : String  = getString(R.string.text_success_data),
                                           message : String =getString(R.string.sub_text_success_data),
                                           func: ((action: Boolean?) -> Unit)? = null) {
    this.showUiTayDialog(model = UiTayDialogModel(image = R.drawable.ic_success,title = title,
        subTitle = message)
    ){
     func?.invoke(it)
    }
}

fun ImageView.setDrawableCircle(nameImage: String?) {
    nameImage?.let {
        val image  = BitmapFactory.decodeFile(it)
        uiTayTryCatch{ this.setImageBitmap(image.converterCircle())}
    }
}