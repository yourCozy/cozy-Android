package com.yourcozy.cozy.views.main.event.comment

data class CommentData(
    val commentIdx: Int,
    val userIdx : Int,
    val activityIdx: Int,
    val profileImg: String,
    val nickname : String,
    val createdAt: String,
    val content: String,
    val mine : Int
)