package com.example.cozy.views.mypage

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R
import kotlin.properties.Delegates

class InterestsViewHolder(itemView : View, val itemClick : (InterestsData, View) -> Unit ) : RecyclerView.ViewHolder(itemView){
    var bookstoreIdx by Delegates.notNull<Int>()
    var bookstoreImg = itemView.findViewById<ImageView>(R.id.iv_interests_bookstore)
    var bookstorename = itemView.findViewById<TextView>(R.id.tv_interests_bookstore_name)
    var bookstoreIntro = itemView.findViewById<TextView>(R.id.tv_interests_detail)
    var location = itemView.findViewById<TextView>(R.id.tv_interests_bookstore_address)
    var tag1 = itemView.findViewById<TextView>(R.id.interests_tag1)
    var tag2 = itemView.findViewById<TextView>(R.id.interests_tag2)
    var tag3 = itemView.findViewById<TextView>(R.id.interests_tag3)

    fun bind(myData: InterestsData) {
        bookstoreIdx = myData.bookstoreIdx
        if(myData.img != ""){
            val resourceId = itemView.context.resources.getIdentifier(myData.img, "drawable", itemView.context.packageName)
            bookstoreImg.setImageResource(resourceId)
        }else{
            bookstoreImg.setImageResource(R.drawable.ex_heftiba_unsplash)
        }
        bookstoreIntro.text = myData.bookstoreIntro
        bookstorename.text = myData.bookstoreName
        location.text = myData.location
        tag1.text = "#" + myData.tag1
        tag2.text = "#" + myData.tag2
        tag3.text = "#" + myData.tag3

        itemView.setOnClickListener{itemClick(myData, itemView)}
    }
}