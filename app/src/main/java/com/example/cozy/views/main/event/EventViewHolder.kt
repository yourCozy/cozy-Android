package com.example.cozy.views.main.event

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
//        Glide.with(itemView).load(myData.img).into(img)
        activityIdx = myData.activityIdx
        img.setImageResource(R.drawable.event_pic)
        day.text = myData.dday.toString()
        title.text = myData.activityName
        info.text = myData.shortIntro
        price.text = myData.price.toString() + "원"
        if(myData.image != null){
            Glide.with(itemView).load(myData.image).into(img)
        }else{
            Glide.with(itemView).load(R.drawable.ex_heftiba_unsplash).into(img)
        }
        price.text = "" + myData.price + "원"
        day.text = "D-" + myData.dday

        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}