package com.example.cozy.network.responseData

import com.example.cozy.views.main.event.comment.CommentData

data class ResponseComment(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<CommentData>
)