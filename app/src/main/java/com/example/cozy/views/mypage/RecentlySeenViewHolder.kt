package com.example.cozy.views.mypage

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import kotlin.properties.Delegates

class RecentlySeenViewHolder(
    itemView: View,
    val itemClick: (RecentlySeenData, View) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

    var boostoreIdx by Delegates.notNull<Int>()
    var bookstoreName : TextView = itemView.findViewById(R.id.tv_recently_seen_title)
    var mainImg : ImageView = itemView.findViewById(R.id.iv_recently_seen_profile)


    fun bind(myData: RecentlySeenData) {
        boostoreIdx = myData.bookstoreIdx
        Glide.with(itemView).load(myData.mainImg).into(mainImg)
//        if(myData.mainImg != ""){
//            val resourceId = itemView.context.resources.getIdentifier(myData.mainImg,"drawable", itemView.context.packageName)
//            mainImg.setImageResource(resourceId)
//        }else{
//            mainImg.setImageResource(R.drawable.ex_socialcut_unsplash)
//        }
        bookstoreName.text = myData.bookstoreName

        itemView.setOnClickListener{ itemClick(myData,itemView)}
    }

}