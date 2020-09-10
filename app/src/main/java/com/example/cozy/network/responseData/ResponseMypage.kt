package com.example.cozy.network.responseData

data class ResponseMypage (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<Myinfo>
)
data class Myinfo(
    val nickname: String,
    val profileImg: String
)