package com.yourcozy.cozy.network.responseData

data class ResponsePwdChange (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Int
)