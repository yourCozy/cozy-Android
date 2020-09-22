package com.yourcozy.cozy.views.main.bookstore

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.responseData.BookstoreFeedData

class BookstoreFeedAdapter(private val context : Context) : RecyclerView.Adapter<BookstoreFeedViewHolder>() {
    var datas = mutableListOf<BookstoreFeedData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookstoreFeedViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bookstore_feed,parent,false)
        return BookstoreFeedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: BookstoreFeedViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}