package com.example.cozy.views.main.event

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R
import kotlin.properties.Delegates

class EventViewHolder(
    itemView: View,
    val itemClick: (EventData, View) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    var activityIdx by Delegates.notNull<Int>()
    var img : ImageView = itemView.findViewById(R.id.activity_img)
    val dday : TextView = itemView.findViewById(R.id.d_day)
    val activityName : TextView = itemView.findViewById(R.id.activity_title)
    val info : TextView = itemView.findViewById(R.id.activity_info)
    val price : TextView = itemView.findViewById(R.id.activity_price)

    fun bind(myData: EventData){
//        Glide.with(itemView).load(myData.img).into(img)
        activityIdx = myData.activityIdx
        img.setImageResource(R.drawable.event_pic)
        dday.text = myData.dday
        activityName.text = myData.activityName
        info.text = myData.shortIntro
        price.text = myData.price

        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}