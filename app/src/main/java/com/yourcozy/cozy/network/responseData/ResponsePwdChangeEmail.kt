package com.yourcozy.cozy.network.responseData

data class ResponsePwdChangeEmail (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: AuthCode
)

data class AuthCode(
    val authCode: Int
)