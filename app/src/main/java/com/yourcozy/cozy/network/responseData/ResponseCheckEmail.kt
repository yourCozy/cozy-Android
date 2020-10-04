package com.yourcozy.cozy.network.responseData

data class ResponseCheckEmail(
    val status : Int,
    val success : Boolean,
    val message : String
)