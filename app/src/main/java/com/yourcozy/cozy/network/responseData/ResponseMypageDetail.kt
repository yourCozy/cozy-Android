package com.yourcozy.cozy.network.responseData

data class ResponseMypageDetail(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data: MyInfoDetail
)

data class MyInfoDetail(
    val nickname: String,
    val profileImg: String,
    val checked: Int
)