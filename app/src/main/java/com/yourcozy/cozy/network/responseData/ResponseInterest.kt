package com.yourcozy.cozy.network.responseData

import com.yourcozy.cozy.views.mypage.Interest.InterestsData

data class ResponseInterest(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<InterestsData>
)