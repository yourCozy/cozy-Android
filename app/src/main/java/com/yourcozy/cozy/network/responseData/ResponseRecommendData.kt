package com.yourcozy.cozy.network.responseData

import com.yourcozy.cozy.views.main.RecommendData

data class ResponseRecommendData (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<RecommendData>
)