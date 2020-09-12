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
    var textNull : TextView = itemView.findViewById(R.id.tv_activity_img_null)

    fun bind(myData: EventData){
        if (myData.image1 != null) {
            Glide.with(itemView).load(myData.image1).into(img)
            textNull.visibility = View.GONE
        }else{
            Glide.with(itemView).load(R.drawable.img_null).into(img)
            textNull.visibility = View.VISIBLE
        }
        activityIdx = myData.activityIdx
        title.text = myData.activityName
        info.text = myData.shortIntro
        price.text = myData.price.toString() + "원"
        price.text = "" + myData.price + "원"
        if(myData.dday < 0){
            day.text = "마감"
        }else if (myData.dday == 0) {
            day.text = "오늘"
        }else{
            day.text = "D-" + myData.dday
        }
        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}