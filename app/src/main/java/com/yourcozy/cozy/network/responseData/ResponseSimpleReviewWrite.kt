package com.yourcozy.cozy.network.responseData

data class ResponseSimpleReviewWrite (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ReviewIdx
)

data class ReviewIdx(
    val reviewIdx : Int
)