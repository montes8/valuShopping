package com.tayler.valushopping.repository.network.abstracts

interface IUserNetwork {
    suspend fun loadTest():Boolean
}