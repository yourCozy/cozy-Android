package com.example.cozy.views.main.event

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import kotlin.properties.Delegates

class EventViewHolder(
    itemView: View,
    val itemClick: (EventData, View) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    var activityIdx by Delegates.notNull<Int>()
    val img : ImageView = itemView.findViewById(R.id.activity_img)
    val day : TextView = itemView.findViewById(R.id.d_day)
    val title : TextView = itemView.findViewById(R.id.activity_title)
    val info : TextView = itemView.findViewById(R.id.activity_info)
    val price : TextView = itemView.findViewById(R.id.activity_price)

    fun bind(myData: EventData){
        Glide.with(itemView).load("myData.image1").into(img)
        Log.d("데이터야",myData.image1)
        activityIdx = myData.activityIdx
        day.text = "D-" + myData.dday.toString()
        title.text = myData.activityName
        info.text = myData.shortIntro
        price.text = myData.price.toString() + "원"
        price.text = "" + myData.price + "원"
        day.text = "D-" + myData.dday

        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}