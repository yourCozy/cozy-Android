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
    val activityName : TextView = itemView.findViewById(R.id.activity_title)
    val shortIntro : TextView = itemView.findViewById(R.id.activity_info)
    var image : ImageView = itemView.findViewById(R.id.activity_img)
    val price : TextView = itemView.findViewById(R.id.activity_price)
    val dday : TextView = itemView.findViewById(R.id.d_day)

    fun bind(myData: EventData){
        activityIdx = myData.activityIdx
        activityName.text = myData.activityName
        shortIntro.text = myData.shortIntro
        if(myData.image1 != null){
            Glide.with(itemView).load(myData.image1).into(image)
        }else{
            Glide.with(itemView).load(R.drawable.ex_heftiba_unsplash).into(image)
        }
        price.text = "" + myData.price + "원"
        dday.text = "D-" + myData.dday

        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}