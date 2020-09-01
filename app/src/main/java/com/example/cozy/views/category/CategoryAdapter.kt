package com.example.cozy.views.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class CategoryAdapter(private val context:Context, val itemClick: (CategoryData, View) -> Unit) : RecyclerView.Adapter<CategoryListViewHolder>() {
    var data = mutableListOf<CategoryData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_activity_intro, parent, false)
        return CategoryListViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holderList: CategoryListViewHolder, position: Int) {
        holderList.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(item: CategoryData){
        data.add(item)
    }
}