package com.example.cozy.network.responseData

data class ResponseCommentChange(
    val status : Int,
    val success : Boolean,
    val message : String,
    //ResponseCommentWrite
    val data : CommentIdx
)