package com.example.cozy.views

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import com.example.cozy.network.responseData.SearchData
import kotlin.properties.Delegates

class SearchViewHolder(itemView : View, val itemClick : (SearchData, View) -> Unit ) : RecyclerView.ViewHolder(itemView) {
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
    var checked by Delegates.notNull<Int>()
    var textNull : TextView = itemView.findViewById(R.id.tv_interest_image_null)

    fun bind(data: SearchData){
        bookstoreIdx = data.bookstoreIdx
        if (data.mainimg != null) {
            Glide.with(itemView).load(data.mainimg).into(bookstoreImg)
            textNull.visibility = View.GONE
        }else{
            Glide.with(itemView).load(R.drawable.img_null).into(bookstoreImg)
            textNull.visibility = View.VISIBLE
        }
        shortIntro1.text = data.shortIntro1
        shortIntro2.text = data.shortIntro2
        bookstorename.text = data.bookstoreName
        location.text = data.location
        if(data.hashtag1 != null) {
            tag1.text = "#" + data.hashtag1
        }
        else{
            tag1.visibility = View.INVISIBLE
        }
        if(data.hashtag2 != null) {
            tag2.text = "#" + data.hashtag2
        }
        else {
            tag2.visibility = View.INVISIBLE
        }
        if(data.hashtag3 != null) {
            tag3.text = "#" + data.hashtag3
        }
        else{
            tag3.visibility = View.INVISIBLE
        }
        checked = data.checked
        bookmark.isSelected = checked != 0
        itemView.setOnClickListener{itemClick(data, itemView)}
    }
}