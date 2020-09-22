package com.yourcozy.cozy.network.responseData

import com.yourcozy.cozy.views.map.MapData

data class ResponseMap (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<MapData>
)