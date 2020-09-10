package com.example.cozy.views.mypage.Notice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class NoticeAdapter(private val context: Context) :
    RecyclerView.Adapter<NoticeViewHolder>() {
    var data = mutableListOf<NoticeData>()
    var expandPosition = -1
    lateinit var iv_up : ImageView
    lateinit var iv_down : ImageView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_notice, parent, false)
        iv_up = view.findViewById(R.id.iv_notice_up)
        iv_down = view.findViewById<ImageView>(R.id.iv_notice_down)
        return NoticeViewHolder(view)
    }

    fun toggleVisibility(position: Int) {
        if (expandPosition != position) {
            notifyItemChanged(position)
            expandPosition = position
        } else {
            expandPosition = -1
        }
        notifyItemChanged(position)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(data[position])
        if (position == expandPosition) {
            (holder.noticeContent).setVisibility(View.VISIBLE)
            iv_up.setVisibility(View.VISIBLE)
            iv_down.visibility = View.GONE
        } else {
            holder.noticeContent.setVisibility(View.GONE)
            iv_up.visibility = View.GONE
            iv_down.visibility = View.VISIBLE
        }
        holder.itemView.setOnClickListener{
            toggleVisibility(position)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(item: NoticeData) {
        data.add(item)
    }


}