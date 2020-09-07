package com.example.cozy.network.responseData

import com.example.cozy.views.main.event.EventDetailData

data class ResponseEventDetail (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<EventDetailData>
)