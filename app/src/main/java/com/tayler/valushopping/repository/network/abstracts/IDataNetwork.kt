package com.tayler.valushopping.repository.network.abstracts

interface IDataNetwork {
    suspend fun loadTest():Boolean
}