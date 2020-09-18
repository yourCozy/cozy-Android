package com.example.cozy.views.mypage.Interest

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
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
    var textNull : TextView = itemView.findViewById(R.id.tv_interest_image_null)

    fun bind(myData: InterestsData) {
        bookstoreIdx = myData.bookstoreIdx
        if (myData.mainImg != null) {
            Glide.with(itemView).load(myData.mainImg).into(bookstoreImg)
            textNull.visibility = View.GONE
        }else{
            textNull.visibility = View.VISIBLE
        }
        shortIntro1.text = myData.shortIntro1
        if(myData.shortIntro2 != null) {
            shortIntro2.text = myData.shortIntro2
        }
        else{
            shortIntro2.visibility = View.GONE
        }
        bookstorename.text = myData.bookstoreName
        location.text = myData.location
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
        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}