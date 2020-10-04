package com.yourcozy.cozy.network.responseData

data class ResponseProfile (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: UserProfileData
)

data class UserProfileData(
    val profileImg: String
)