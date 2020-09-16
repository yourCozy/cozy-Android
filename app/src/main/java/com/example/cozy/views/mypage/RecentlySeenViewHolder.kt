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
    var textNull: TextView = itemView.findViewById(R.id.tv_recently_seen_null)


    fun bind(myData: RecentlySeenData) {
        boostoreIdx = myData.bookstoreIdx
        if (myData.mainImg != null) {
            Glide.with(itemView).load(myData.mainImg).into(mainImg)
            textNull.visibility = View.GONE
        }else{
            Glide.with(itemView).load(R.drawable.img_null).into(mainImg)
            textNull.visibility = View.VISIBLE
        }
        bookstoreName.text = myData.bookstoreName

        itemView.setOnClickListener{ itemClick(myData,itemView)}
    }

}