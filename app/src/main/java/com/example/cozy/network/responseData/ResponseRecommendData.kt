package com.example.cozy.network.responseData

import com.example.cozy.views.main.RecommendData

data class ResponseRecommendData (
    val status: String,
    val success: Boolean,
    val message: String,
    val data: List<RecommendData>
)