package com.yourcozy.cozy.network.responseData

data class ResponseSignup(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : UserData
)

data class UserData(
    val userIdx : Int,
    val jwtToken : String
)