package com.tayler.valushopping.component

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import com.tayler.valushopping.R

class CrossProgressBarFull(context: Context, themeResId: Int) :
    AlertDialog(context, themeResId) {

    init {
        setView(context)
    }

    private fun setView(context: Context) {
        window?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.transparency_white)))
    }

    override fun show() {
        super.show()
        this.setContentView(R.layout.dialog_progress_full)
        this.setCancelable(false)
    }
}