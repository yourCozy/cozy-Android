package com.example.cozy.network.responseData

data class ResponseSignin(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : UserInfoData
)

data class UserInfoData(
    val userIdx : Int,
    val nickname : String,
    val id : String,
    val profile : String,
    val jwtToken : String,
    val is_logined : Int
)