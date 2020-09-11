package com.example.cozy.network.responseData

import com.example.cozy.views.mypage.Interest.InterestsData

data class ResponseInterest(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<InterestsData>
)