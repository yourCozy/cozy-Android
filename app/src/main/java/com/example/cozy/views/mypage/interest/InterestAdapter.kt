package com.example.cozy.views.mypage.interest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R



class InterestAdapter (private val context : Context, val itemClick: (InterestData, View) -> Unit) : RecyclerView.Adapter<InterestViewHolder>(){
    var data = mutableListOf<InterestData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_interest,parent,false)
        return InterestViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
        holder.bind(data[position])

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

}