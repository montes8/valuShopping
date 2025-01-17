package com.tayler.valushopping.utils.result

import androidx.lifecycle.MutableLiveData
import com.gb.vale.uitaylibrary.utils.EventUiTay

object ValeResult {

    val eventUpdateListProduct:MutableLiveData<EventUiTay<Boolean>> by lazy{
        MutableLiveData<EventUiTay<Boolean>>()
    }
}