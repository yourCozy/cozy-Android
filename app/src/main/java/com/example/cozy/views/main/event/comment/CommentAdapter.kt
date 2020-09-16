package com.example.cozy.views.main.event.comment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R
import com.example.cozy.views.main.RecommendData
import com.example.cozy.views.map.MapViewHolder

class CommentAdapter(private val context : Context, val modify_click: (Int, String) -> Unit) : RecyclerView.Adapter<CommentViewHolder>(){

    var data = mutableListOf<CommentData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("댓글", data.size.toString())
        return data.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(data[position])

        //유저의 글만 수정 삭제 보이게

        //수정 및 삭제 통신
        holder.comment_change.setOnClickListener{modify_click(position,holder.commentText.text.toString())}

    }


}