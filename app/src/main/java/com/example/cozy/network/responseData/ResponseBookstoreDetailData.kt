package com.example.cozy.network.responseData

data class ResponseBookstoreDetailData (
    val status: String,
    val success: Boolean,
    val message: String,
    val data: List<BookstoreDetailData>
)

data class BookstoreDetailData(
    val bookstoreIdx: Int,
    val bookstoreName: String,
    val mainImg: String,
    val profileImg: String,
    val shortIntro1: String,
    val shortIntro2: String,
    val hashtag1: String,
    val hashtag2: String,
    val hashtag3: String,
    val sectionIdx: Int,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val businessHours: String,
    val dayoff: String,
    val tel: String,
    val description: String,
    val activities: String,
    val image1: String,
    val image2: String,
    val image3: String,
    val bookmark: Int,
    val tasteCount: Int,
    val checked: Int
)