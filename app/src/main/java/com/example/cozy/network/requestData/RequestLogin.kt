package com.example.cozy.network.requestData

data class RequestLogin(
    val email : String,
    val nickname: String,
    val refreshToken : String
)