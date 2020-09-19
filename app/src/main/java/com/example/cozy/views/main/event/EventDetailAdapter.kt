package com.example.cozy.views.main.event

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R

class EventDetailAdapter (private val context : Context): RecyclerView.Adapter<EventDetailViewHolder>(){

    var data = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event_items, parent, false)
    return EventDetailViewHolder(
        view
    )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: EventDetailViewHolder, position: Int) {
        Glide.with(context).load(data[position]).into(holder.event_rv_img)

    }

}