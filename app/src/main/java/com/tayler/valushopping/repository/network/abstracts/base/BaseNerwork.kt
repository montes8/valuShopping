package com.tayler.valushopping.repository.network.abstracts.base

import android.content.Context
import com.tayler.valushopping.repository.network.exception.MyNetworkException
import com.tayler.valushopping.utils.isAirplaneModeActive
import com.tayler.valushopping.utils.isConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class BaseNetwork @Inject constructor(@ApplicationContext val context : Context){

    suspend fun <T> executeWithConnection(block : suspend()->T):T{
        if (!context.applicationContext.isConnected() || context.applicationContext.isAirplaneModeActive()){
            throw MyNetworkException()
        }
        return block()
    }

}