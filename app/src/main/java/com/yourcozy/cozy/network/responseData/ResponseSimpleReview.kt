package com.yourcozy.cozy.network.responseData

data class ResponseSimpleReview (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: SimpleReviewData
)

data class SimpleReviewData(
    val bookstoreIdx : Int,
    val avg_fac : Int,
    val avg_book : Int,
    val avg_act : Int,
    val avg_food : Int
)