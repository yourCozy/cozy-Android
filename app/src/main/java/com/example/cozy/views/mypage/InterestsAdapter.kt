package com.example.cozy.views.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class InterestsAdapter(val context: Context, val itemClick: (InterestsData, View) -> Unit) :
    RecyclerView.Adapter<InterestsViewHolder>() {
    var data = mutableListOf<InterestsData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestsViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_interests_bookstore, parent, false)
        return InterestsViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: InterestsViewHolder, position: Int) {
        holder.bind(data[position])
        //북마크 해제 온클릭리스터 추가
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(item: InterestsData) {
        data.add(item)
    }
}