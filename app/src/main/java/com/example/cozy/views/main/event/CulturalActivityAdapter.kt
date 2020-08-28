package com.example.cozy.views.main.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class CulturalActivityAdapter (private val context : Context, val itemClick: (CulturalActivityData, View) -> Unit) : RecyclerView.Adapter<CulturalActivityViewHolder>(){
    var datas = mutableListOf<CulturalActivityData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CulturalActivityViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_activity,parent,false)
        return CulturalActivityViewHolder(
            view,
            itemClick
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: CulturalActivityViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    fun addItem(item : CulturalActivityData){
        datas.add(item)
    }
}