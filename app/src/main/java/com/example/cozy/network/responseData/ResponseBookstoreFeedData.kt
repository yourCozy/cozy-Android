package com.example.cozy.network.responseData

class ResponseBookstoreFeedData (
    val status: String,
    val success: Boolean,
    val message: String,
    val data: List<BookstoreFeedData>
)

class BookstoreFeedData(
    val bookstoreIdx: Int,
    val image1: String,
    val image2: String,
    val image3: String,
    val description: String
)