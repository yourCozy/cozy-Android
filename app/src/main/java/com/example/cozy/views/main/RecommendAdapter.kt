package com.example.cozy.views.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class RecommendAdapter (private val context : Context, val itemClick: (RecommendData, View) -> Unit) : RecyclerView.Adapter<RecommendViewHolder>(){
    var datas = mutableListOf<RecommendData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recommend,parent,false)
        return RecommendViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.save.setOnClickListener{
            Toast.makeText(context, "북마크!", Toast.LENGTH_SHORT).show()
            if (holder.save.isSelected){
                holder.save.isSelected = false
            }
            else{
                holder.save.isSelected = true
            }
        }
    }

    fun addItem(item : RecommendData){
        datas.add(item)
    }
}