package com.example.cozy.views.mypage

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R
import kotlinx.android.synthetic.main.item_recently_seen.view.*
import kotlin.properties.Delegates

class RecentlySeenListViewHolder(
    itemView: View,
    val itemClick: (RecentlySeenData, View) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

    var boostoreIdx by Delegates.notNull<Int>()
    var profile : ImageView = itemView.findViewById(R.id.iv_recently_seen_profile)
    var bookstoreName : TextView = itemView.findViewById(R.id.tv_recently_seen_title)

    fun bind(myData: RecentlySeenData) {
        boostoreIdx = myData.bookstoreIdx
        if(myData.profile != ""){
            val resourceId = itemView.context.resources.getIdentifier(myData.profile,"drawable", itemView.context.packageName)
            profile.setImageResource(resourceId)
        }else{
            profile.setImageResource(R.drawable.ex_socialcut_unsplash)
        }
        bookstoreName.text = myData.bookstoreName

        itemView.setOnClickListener{ itemClick(myData,itemView)}
    }

}