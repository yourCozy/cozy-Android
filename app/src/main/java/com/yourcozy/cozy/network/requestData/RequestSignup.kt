package com.yourcozy.cozy.network.requestData

data class RequestSignup(
    val email : String,
    val nickname : String,
    val password : String,
    val passwordConfirm : String
)