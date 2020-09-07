package com.example.cozy.views.main.event

import java.util.*

data class EventDetailData(
    val activityIdx : Int,
    val activityName : String,
    val bookstoreIdx : Int,
    val categoryIdx : Int,
    val categoryName : String,
    val price : Int,
    val limitation : Int,
    val shortIntro : String,
    val introduction : String,
    val period : String,
    val deadline : Date,
    val img : String,
    val createdAt : String
)