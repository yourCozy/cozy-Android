package com.example.cozy.views.main.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class EventAdapter (private val context : Context, val itemClick: (EventData, View) -> Unit) : RecyclerView.Adapter<EventViewHolder>(){
    var datas = mutableListOf<EventData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_activity,parent,false)
        return EventViewHolder(
            view,
            itemClick
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    fun addItem(item : EventData){
        datas.add(item)
    }
}