package com.example.cozy.views.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import kotlin.properties.Delegates

class RecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var bookstoreIdx by Delegates.notNull<Int>()
    var profile : ImageView = itemView.findViewById(R.id.rec_img)
    var tag1 : TextView = itemView.findViewById(R.id.rec_tag1)
    var tag2 : TextView = itemView.findViewById(R.id.rec_tag2)
    var tag3 : TextView = itemView.findViewById(R.id.rec_tag3)
    var intro : TextView = itemView.findViewById(R.id.rec_intro)
    var name : TextView = itemView.findViewById(R.id.rec_name)
    var address : TextView = itemView.findViewById(R.id.rec_address)

    fun bind(myData: RecommendData){
        bookstoreIdx = myData.bookstoreIdx
//        Glide.with(itemView).load(myData.profile).into(profile)
        profile.setBackgroundResource(R.drawable.recommend_img)
        tag1.text = "#" + myData.tag1
        tag2.text = "#" + myData.tag2
        tag3.text = "#" + myData.tag3
        intro.text = myData.intro
        name.text = myData.bookstoreName
        address.text = myData.location
//        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}