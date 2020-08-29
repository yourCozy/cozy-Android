package com.example.cozy.views.map

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R


class MapAdapter (private val context : Context, val itemClick: (MapData) -> Unit) : RecyclerView.Adapter<MapViewHolder>(){
    var data = mutableListOf<MapData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bookstore_list,parent,false)
        return MapViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun addItem(item : MapData){
        data.add(item)
    }
}