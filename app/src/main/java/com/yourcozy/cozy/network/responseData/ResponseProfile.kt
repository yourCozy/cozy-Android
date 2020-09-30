package com.yourcozy.cozy.network.responseData

data class ResponseProfile (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: UserProfileData
)

data class UserProfileData(
    val userIdx: Int,
    val nickname: String,
    val email: String,
    val profileImg: String
)