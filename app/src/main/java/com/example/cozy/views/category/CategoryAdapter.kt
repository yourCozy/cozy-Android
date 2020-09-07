package com.example.cozy.views.category

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class CategoryAdapter(private val context:Context, val data : MutableList<CategoryData>, val itemClick: (CategoryData, View) -> Unit) : RecyclerView.Adapter<CategoryViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_activity_intro, parent, false)
        return CategoryViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(item: CategoryData){
        data.add(item)
    }
}