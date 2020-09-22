package com.yourcozy.cozy.network.responseData

import com.yourcozy.cozy.views.main.event.EventData

data class ResponseEvent(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<EventData>
)