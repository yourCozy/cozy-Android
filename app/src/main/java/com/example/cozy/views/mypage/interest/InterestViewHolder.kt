package com.example.cozy.views.mypage.interest

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R

class InterestViewHolder (itemView: View, val itemClick : (InterestData,View) -> Unit) : RecyclerView.ViewHolder(itemView) {
    val img = itemView.findViewById<ImageView>(R.id.img_interest_bookstore)
    val bookstoreName = itemView.findViewById<TextView>(R.id.interest_place)
    val location = itemView.findViewById<TextView>(R.id.interest_address)
    val tag1 = itemView.findViewById<TextView>(R.id.tv_interest_tag1)
    val tag2 = itemView.findViewById<TextView>(R.id.tv_interest_tag2)
    val tag3 = itemView.findViewById<TextView>(R.id.tv_interest_tag3)
    val intro = itemView.findViewById<TextView>(R.id.interest_intro)
    var save: ImageButton = itemView.findViewById(R.id.interest_save_btn)


    fun bind(data: InterestData)
    {
        Glide.with(itemView).load(data.img).into(img)
        bookstoreName.text = data.bookstoreName
        location.text = data.location
        tag1.text = "#"+ data.tag1
        tag2.text = "#"+ data.tag2
        tag3.text = "#"+ data.tag3
        intro.text = data.intro
        itemView.setOnClickListener{itemClick(data,itemView)}
    }

}