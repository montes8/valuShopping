package com.tayler.valushopping.repository.network.api

import com.tayler.valushopping.repository.network.ServiceApi
import com.tayler.valushopping.repository.network.abstracts.IDataNetwork
import com.tayler.valushopping.repository.network.abstracts.base.BaseNetwork
import javax.inject.Inject

class DataNetwork @Inject constructor(private val serviceApi : ServiceApi,private val base : BaseNetwork):
    IDataNetwork {
    override suspend fun loadTest(): Boolean {
        return true
    }
}