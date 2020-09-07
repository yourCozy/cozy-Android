package com.example.cozy.views.mypage

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class RecentlySeenAdapter(
    private val context: Context,
    val data : MutableList<RecentlySeenData>,
    val itemClick: (RecentlySeenData, View) -> Unit
) : RecyclerView.Adapter<RecentlySeenViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlySeenViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recently_seen, parent, false)
        return RecentlySeenViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: RecentlySeenViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(item: RecentlySeenData) {
        data.add(item)
    }
}