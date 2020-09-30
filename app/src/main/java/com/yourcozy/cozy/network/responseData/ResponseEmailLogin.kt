package com.yourcozy.cozy.network.responseData

data class ResponseEmailLogin(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : UserEmailInfoData
)

data class UserEmailInfoData(
    val userIdx : Int,
    val nickname : String,
    val email : String,
    val accessToken : String,
    val is_logined : Int
)

