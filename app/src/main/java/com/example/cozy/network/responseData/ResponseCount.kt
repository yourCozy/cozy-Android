package com.example.cozy.network.responseData

data class ResponseCount(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<MapCount>
)

data class MapCount(
    val sectionIdx : Int,
    val count : Int
)