package com.yourcozy.cozy.network.responseData



data class ResponseCommentWrite(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : CommentIdx
)

data class CommentIdx(
    val commentIdx: Int
)