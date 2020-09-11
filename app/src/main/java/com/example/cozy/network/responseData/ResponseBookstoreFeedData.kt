package com.example.cozy.network.responseData

data class ResponseBookstoreFeedData (
    val status: String,
    val success: Boolean,
    val message: String,
    val data: List<BookstoreFeedData>
)

data class BookstoreFeedData(
    val image: String,
    val text: String
)