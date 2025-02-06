package com.tayler.valushopping.utils

import android.annotation.SuppressLint
import com.tayler.valushopping.repository.network.model.ParamResponse
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun ParamResponse.validateHourApp():Boolean{
    val dateFormat = SimpleDateFormat("HH:mm:ss")
    val date = Date()
    val hourCurrent = dateFormat.format(date).replace(":","").toInt()
    val hourStart = dateFormat.parse(this.hourStart?: HOUR_START_DEFAULT) as Date
    val hourEnd = dateFormat.parse(this.hourEnd?: HOUR_END_DEFAULT) as Date
    val hourStartParse = dateFormat.format(hourStart).replace(":","").toInt()
    val hourEndParse = dateFormat.format(hourEnd).replace(":","").toInt()
    val validStart = hourCurrent > hourStartParse
    val validEnd = hourCurrent < hourEndParse
    return validStart && validEnd
}