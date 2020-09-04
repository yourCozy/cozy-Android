package com.example.cozy.views.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class NoticeAdapter(private val context: Context, val itemClick: (NoticeData, View) -> Unit) :
    RecyclerView.Adapter<NoticeViewHolder>() {
    var data = mutableListOf<NoticeData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_notice, parent, false)
        return NoticeViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(item: NoticeData) {
        data.add(item)
    }


}