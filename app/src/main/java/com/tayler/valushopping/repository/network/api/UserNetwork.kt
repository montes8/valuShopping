package com.tayler.valushopping.repository.network.api

import com.tayler.valushopping.repository.network.ServiceApi
import com.tayler.valushopping.repository.network.abstracts.IUserNetwork
import com.tayler.valushopping.repository.network.abstracts.base.BaseNetwork
import javax.inject.Inject

class UserNetwork @Inject constructor(private val serviceApi : ServiceApi, private val base : BaseNetwork):
    IUserNetwork {
    override suspend fun loadTest(): Boolean {
        return true
    }
}