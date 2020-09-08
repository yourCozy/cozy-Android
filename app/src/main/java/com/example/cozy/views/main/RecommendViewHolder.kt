package com.example.cozy.views.main

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import kotlin.properties.Delegates

class RecommendViewHolder(itemView: View, val itemClick: (RecommendData, View) -> Unit) : RecyclerView.ViewHolder(itemView) {
    var bookstoreIdx by Delegates.notNull<Int>()
    var profile : ImageView = itemView.findViewById(R.id.rec_img)
    var tag1 : TextView = itemView.findViewById(R.id.rec_tag1)
    var tag2 : TextView = itemView.findViewById(R.id.rec_tag2)
    var tag3 : TextView = itemView.findViewById(R.id.rec_tag3)
    var intro1 : TextView = itemView.findViewById(R.id.rec_intro1)
    var intro2 : TextView = itemView.findViewById(R.id.rec_intro2)
    var name : TextView = itemView.findViewById(R.id.rec_name)
    var address : TextView = itemView.findViewById(R.id.rec_address)
    var save : ImageButton = itemView.findViewById(R.id.btn_save)
    var checked by Delegates.notNull<Int>()

    fun bind(myData: RecommendData){
        bookstoreIdx = myData.bookstoreIdx
        Glide.with(itemView).load("https://t1.daumcdn.net/cfile/tistory/21053641544FEA7816").into(profile)
        profile.setBackgroundResource(R.drawable.recommend_img)
        tag1.text = "#" + myData.hashtag1
        tag2.text = "#" + myData.hashtag2
        tag3.text = "#" + myData.hashtag3
        intro1.text = myData.shortIntro1
        intro2.text = myData.shortIntro2
        name.text = myData.bookstoreName
        address.text = myData.location
        checked = myData.checked
        save.isSelected = checked != 0
        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}