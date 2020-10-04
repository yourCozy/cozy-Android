package com.yourcozy.cozy.network.responseData

data class ResponseNickChanged(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data: List<NewNickname>
)

data class NewNickname(
    val updatedNickname : String
)