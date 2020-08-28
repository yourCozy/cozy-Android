package com.example.cozy.views.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class ActivityAdapter(private val context:Context) : RecyclerView.Adapter<ActivityViewHolder>() {
    var data = mutableListOf<ActivityData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_activity_intro, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}