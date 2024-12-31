package com.tayler.valushopping.repository.network.exception

import com.tayler.valushopping.utils.EMPTY_VALE

data class ApiException(val code: Int = 0,val title: String = EMPTY_VALE, val meessage: String = EMPTY_VALE): Exception()

class GenericException() : Exception()

class MyNetworkException() : Exception()

class UnAuthorizedException() : Exception()