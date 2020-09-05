package com.example.cozy.views.map

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R

class MapViewHolder (itemView: View, val itemClick: (MapData, View) -> Unit) : RecyclerView.ViewHolder(itemView){
    //val bookstoreIdx = itemView.findViewById<TextView>(R.id.tv_bookstore_name)
    val img1 = itemView.findViewById<ImageView>(R.id.img_bookstore)
    val bookstoreName = itemView.findViewById<TextView>(R.id.tv_bookstore_name)
    val location = itemView.findViewById<TextView>(R.id.tv_bookstore_address)
    val tag1 = itemView.findViewById<TextView>(R.id.tv_bookstore_tag1)
    val tag2 = itemView.findViewById<TextView>(R.id.tv_bookstore_tag2)
    val tag3 = itemView.findViewById<TextView>(R.id.tv_bookstore_tag3)
    var bookmark : ImageButton = itemView.findViewById(R.id.map_save_btn)

    fun bind(data: MapData)
    {
        Glide.with(itemView).load(data.mainImg).into(img1)
        bookstoreName.text = data.bookstoreName
        location.text = data.location
        tag1.text = "#"+ data.hashtag1
        tag2.text = "#"+ data.hashtag2
        tag3.text = "#"+ data.hashtag3
        bookmark.isSelected = data.checked != 0
        itemView.setOnClickListener{itemClick(data, itemView)}
    }


}