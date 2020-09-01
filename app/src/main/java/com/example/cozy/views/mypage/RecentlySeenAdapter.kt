package com.example.cozy.views.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class RecentlySeenAdapter(
    private val context: Context,
    val itemClick: (RecentlySeenData, View) -> Unit
) : RecyclerView.Adapter<RecentlySeenListViewHolder>() {
    var data = mutableListOf<RecentlySeenData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlySeenListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recently_seen, parent, false)
        return RecentlySeenListViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: RecentlySeenListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(item: RecentlySeenData) {
        data.add(item)
    }
}