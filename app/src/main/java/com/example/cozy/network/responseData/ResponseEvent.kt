package com.example.cozy.network.responseData

import com.example.cozy.views.main.event.EventData

data class ResponseEvent(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<EventData>
)