package com.example.cozy.network.responseData

data class ResponsePreference (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Preference
)

data class Preference(
    val tasteIdx: Int,
    val userIdx: Int,
    val tastes: String
)