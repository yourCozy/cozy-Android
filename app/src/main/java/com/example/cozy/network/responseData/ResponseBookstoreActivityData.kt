package com.example.cozy.network.responseData

import com.example.cozy.views.main.event.EventData

data class ResponseBookstoreActivityData (
    val status: String,
    val success: Boolean,
    val message: String,
    val data: List<EventData>
)