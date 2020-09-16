package com.example.cozy.views.main.event.comment

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.DialogComment
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue

class CommentAdapter(private val context : Context, val modify_click: (Int, String) -> Unit) : RecyclerView.Adapter<CommentViewHolder>() {

    var data = mutableListOf<CommentData>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("댓글", data.size.toString())
        return data.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(data[position])

        //유저의 글만 수정 삭제 보이게
        if (data[position].mine == 0) {
            holder.comment_change.visibility = View.GONE
            holder.comment_line.visibility = View.GONE
            holder.comment_delete.visibility = View.GONE
        } else {
            val sharedPref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "token")
            val header = mutableMapOf<String, String?>()
            header["Content-Type"] = "application/json"
            header["token"] = token

            //삭제
            holder.comment_delete.setOnClickListener {
                val customDialog = DialogComment(context!!)
                customDialog.start()
                customDialog.setOnOKClickedListener {
                    RequestToServer.service.requestCommentDel(data[position].commentIdx, header)
                        .customEnqueue(
                            onError = {},
                            onSuccess = {
                                if (it.body()!!.success) {
                                    data.removeAt(position)
                                    notifyItemRemoved(position)
                                    notifyItemRangeChanged(position, data.size)
                                }
                            }
                        )
                }
            }
        }

        //수정
        holder.comment_change.setOnClickListener { modify_click(holder.commentIdx, holder.commentText.text.toString()) }
    }
}


