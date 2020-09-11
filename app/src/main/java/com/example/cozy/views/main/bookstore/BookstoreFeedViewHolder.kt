package com.example.cozy.views.main.bookstore

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import com.example.cozy.network.responseData.BookstoreFeedData

class BookstoreFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val img: ImageView = itemView.findViewById(R.id.bookstore_feed_img)
    val text: TextView = itemView.findViewById(R.id.bookstore_feed_text)

    fun bind(data: BookstoreFeedData){
        Glide.with(itemView).load(data.image).into(img)
        text.text = data.text
    }
}