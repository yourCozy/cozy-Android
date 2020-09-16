package com.example.cozy.views.main.event.comment

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import kotlin.properties.Delegates

class CommentViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    var activityIdx by Delegates.notNull<Int>()
    var commentIdx by Delegates.notNull<Int>()
    val commentProfile = itemView.findViewById<ImageView>(R.id.comment_profile)
    val commentName = itemView.findViewById<TextView>(R.id.comment_name)
    val commentDate = itemView.findViewById<TextView>(R.id.comment_date)
    val commentText = itemView.findViewById<TextView>(R.id.comment_text)
    val comment_change = itemView.findViewById<TextView>(R.id.comment_change)
    val comment_delete = itemView.findViewById<TextView>(R.id.comment_delete)
    val comment_line = itemView.findViewById<ImageView>(R.id.comment_line)

    fun bind(data: CommentData){
        activityIdx = data.activityIdx
        commentIdx = data.commentIdx
        Glide.with(itemView).load(data.profileImg).into(commentProfile)
        commentName.text = data.nickname
        Log.d("댓글", "{$data.commentName}")
        commentDate.text = data.createdAt
        commentText.text = data.content
    }
}