package com.tayler.valushopping.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.gb.vale.uitaylibrary.dialog.UiTayDialogModel
import com.gb.vale.uitaylibrary.utils.converterCircle
import com.gb.vale.uitaylibrary.utils.setOnClickUiTayDelay
import com.gb.vale.uitaylibrary.utils.showUiTayDialog
import com.gb.vale.uitaylibrary.utils.showUiTayDialogLayout
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


fun AppCompatActivity.dialogZoom(image:Bitmap
){
    showUiTayDialogLayout(R.layout.dialog_zoom_product, true) { dialog ->
        val ctnZoom = dialog.uiTayView.findViewById<ConstraintLayout>(R.id.ctnZoomProduct)
        val cardZoom = dialog.uiTayView.findViewById<CardView>(R.id.cardZoomProduct)
        val imgZoom = dialog.uiTayView.findViewById<ImageView>(R.id.imgZoomProduct)
        cardZoom.post {
            val width: Int = cardZoom.width
            val param =
                ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, width)
            cardZoom.layoutParams = param
            imgZoom.setImageBitmap(image)
        }

        ctnZoom.setOnClickUiTayDelay {
            dialog.dismiss()}
        cardZoom.setOnClickUiTayDelay{
            dialog.dismiss()
        }

    }
}