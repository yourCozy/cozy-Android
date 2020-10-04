package com.yourcozy.cozy.network.responseData

data class ResponseFindPW(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : toEmail
)

data class toEmail(
    val toEmail : String,
    val subject : String
)