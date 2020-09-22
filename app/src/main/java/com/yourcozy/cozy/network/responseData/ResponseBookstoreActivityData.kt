package com.yourcozy.cozy.network.responseData

import com.yourcozy.cozy.views.main.event.EventData

data class ResponseBookstoreActivityData (
    val status: String,
    val success: Boolean,
    val message: String,
    val data: List<EventData>
)