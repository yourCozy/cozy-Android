package com.yourcozy.cozy.network.responseData

import com.yourcozy.cozy.views.main.RecommendData

data class ResponseRecommendData (
    val status: String,
    val success: Boolean,
    val message: String,
    val data: List<RecommendData>
)