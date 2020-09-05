package com.example.cozy.views.map

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R


class MapAdapter (private val context : Context, val data : MutableList<MapData>, val itemClick: (MapData, View) -> Unit) : RecyclerView.Adapter<MapViewHolder>(){

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bookstore_list,parent,false)
        return MapViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        holder.bind(data[position])

        holder.bookmark.setOnClickListener{
            Toast.makeText(context, "북마크!", Toast.LENGTH_SHORT).show()
            if (holder.bookmark.isSelected){
                holder.bookmark.isSelected = false
            }
            else{
                holder.bookmark.isSelected = true
            }
        }
    }

    fun addItem(item : MapData){
        data.add(item)
    }
}