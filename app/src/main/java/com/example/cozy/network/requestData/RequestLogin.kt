package com.example.cozy.network.requestData

data class RequestLogin(
    val id : String,
    val nickname: String,
    val refreshToken : String
)