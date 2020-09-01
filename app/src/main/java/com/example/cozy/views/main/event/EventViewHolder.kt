package com.example.cozy.views.main.event

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R

class EventViewHolder(
    itemView: View,
    val itemClick: (EventData, View) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    val img : ImageView = itemView.findViewById(R.id.activity_img)
    val day : TextView = itemView.findViewById(R.id.d_day)
    val title : TextView = itemView.findViewById(R.id.activity_title)
    val info : TextView = itemView.findViewById(R.id.activity_info)
    val price : TextView = itemView.findViewById(R.id.activity_price)

    fun bind(myData: EventData){
//        Glide.with(itemView).load(myData.img).into(img)
        img.setImageResource(R.drawable.event_pic)
        day.text = myData.day
        title.text = myData.activity_title
        info.text = myData.activiry_info
        price.text = myData.activity_price
        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}