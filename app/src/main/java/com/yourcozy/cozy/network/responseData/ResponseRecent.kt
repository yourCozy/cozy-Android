package com.yourcozy.cozy.network.responseData

import com.yourcozy.cozy.views.mypage.RecentlySeenData

data class ResponseRecent (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data : List<RecentlySeenData>
)