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
    var mainImg : ImageView = itemView.findViewById(R.id.rec_img)
    var tag1 : TextView = itemView.findViewById(R.id.rec_tag1)
    var tag2 : TextView = itemView.findViewById(R.id.rec_tag2)
    var tag3 : TextView = itemView.findViewById(R.id.rec_tag3)
    var intro1 : TextView = itemView.findViewById(R.id.rec_intro1)
    var intro2 : TextView = itemView.findViewById(R.id.rec_intro2)
    var name : TextView = itemView.findViewById(R.id.rec_name)
    var address : TextView = itemView.findViewById(R.id.rec_address)
    var save : ImageButton = itemView.findViewById(R.id.btn_save)
    var checked by Delegates.notNull<Int>()
    var textNull : TextView = itemView.findViewById(R.id.tv_recommend_image_null)

    fun bind(myData: RecommendData){
        bookstoreIdx = myData.bookstoreIdx
        if (myData.mainImg != null) {
            Glide.with(itemView).load(myData.mainImg).into(mainImg)
            textNull.visibility = View.GONE
        }else{
            textNull.visibility = View.VISIBLE
        }

        if(myData.hashtag1 != null) {
            tag1.text = "#" + myData.hashtag1
        }
        else{
            tag1.visibility = View.INVISIBLE
        }
        if(myData.hashtag2 != null) {
            tag2.text = "#" + myData.hashtag2
        }
        else {
            tag2.visibility = View.INVISIBLE
        }
        if(myData.hashtag3 != null) {
            tag3.text = "#" + myData.hashtag3
        }
        else{
            tag3.visibility = View.INVISIBLE
        }
        if(myData.shortIntro2 != null) {
            intro1.text = myData.shortIntro1
            intro2.text = myData.shortIntro2
        }
        else{
            intro1.visibility = View.GONE
            intro2.text = myData.shortIntro1
        }
        name.text = myData.bookstoreName
        address.text = myData.location
        checked = myData.checked
        save.isSelected = checked != 0
        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}