package com.example.cozy.views.main.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R


class EventDetailAdapter (private val context: Context): RecyclerView.Adapter<EventDetailViewHolder>(){

    var data = mutableListOf<EventDetailData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventDetailViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_event_items, parent, false)
    return EventDetailViewHolder(
        view
    )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: EventDetailViewHolder, position: Int) {
        holder.bind(data[position])
    }
}