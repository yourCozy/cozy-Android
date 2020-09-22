package com.yourcozy.cozy.network.responseData

import com.yourcozy.cozy.views.main.event.EventDetailData

data class ResponseEventDetail (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<EventDetailData>
)