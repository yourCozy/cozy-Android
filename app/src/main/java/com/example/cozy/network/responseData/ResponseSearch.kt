package com.example.cozy.network.responseData

data class ResponseSearch (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<SearchData>
)

data class SearchData(
    val bookstoreIdx : Int,
    val bookstoreName : String,
    val location : String,
    val shortIntro1 : String,
    val shortIntro2 : String,
    val mainImg : String,
    val hashtag1 : String,
    val hashtag2 : String,
    val hashtag3 : String,
    val checked: Int,
    val count : Int
)