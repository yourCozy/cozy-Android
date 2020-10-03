package com.yourcozy.cozy.network

data class ErrorResponse (
    val status: Int,
    val success: Boolean,
    val message: String
)