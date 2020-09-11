package com.example.cozy.views.mypage.Interest

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import com.example.cozy.views.mypage.Interest.InterestsData
import kotlin.properties.Delegates

class InterestsViewHolder(itemView : View, val itemClick : (InterestsData, View) -> Unit ) : RecyclerView.ViewHolder(itemView){
    var bookstoreIdx by Delegates.notNull<Int>()
    var bookstoreImg = itemView.findViewById<ImageView>(R.id.img_interest_bookstore)
    var bookstorename = itemView.findViewById<TextView>(R.id.interest_place)
    var shortIntro1 = itemView.findViewById<TextView>(R.id.interest_intro1)
    var shortIntro2 = itemView.findViewById<TextView>(R.id.interest_intro2)
    var location = itemView.findViewById<TextView>(R.id.interest_address)
    var tag1 = itemView.findViewById<TextView>(R.id.tv_interest_tag1)
    var tag2 = itemView.findViewById<TextView>(R.id.tv_interest_tag2)
    var tag3 = itemView.findViewById<TextView>(R.id.tv_interest_tag3)
    var bookmark : ImageButton = itemView.findViewById(R.id.interest_save_btn)

    fun bind(myData: InterestsData) {
        bookstoreIdx = myData.bookstoreIdx
        Glide.with(itemView).load(myData.mainimg).into(bookstoreImg)
        shortIntro1.text = myData.shortIntro1
        shortIntro2.text = myData.shortIntro2
        bookstorename.text = myData.bookstoreName
        location.text = myData.location
        tag1.text = "#" + myData.tag1
        tag2.text = "#" + myData.tag2
        tag3.text = "#" + myData.tag3
        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}