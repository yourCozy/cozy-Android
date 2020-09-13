package com.example.cozy.views.main.event.comment

data class CommentData(
    val activityIdx: Int,
    val commentProfile: String,
    val commentName: String,
    val commentDate: String,
    val commentText: String
)