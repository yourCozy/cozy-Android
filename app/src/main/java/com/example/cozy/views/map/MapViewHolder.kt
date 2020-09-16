package com.example.cozy.views.map


import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import kotlin.properties.Delegates

class MapViewHolder(itemView: View, val itemClick: (MapData, View) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    val img1 = itemView.findViewById<ImageView>(R.id.img_bookstore)
    val bookstoreName = itemView.findViewById<TextView>(R.id.tv_bookstore_name)
    val location = itemView.findViewById<TextView>(R.id.tv_bookstore_address)
    val tag1 = itemView.findViewById<TextView>(R.id.tv_bookstore_tag1)
    val tag2 = itemView.findViewById<TextView>(R.id.tv_bookstore_tag2)
    val tag3 = itemView.findViewById<TextView>(R.id.tv_bookstore_tag3)
    var bookmark: ImageButton = itemView.findViewById(R.id.map_save_btn)
    var checked by Delegates.notNull<Int>()
    var textNull = itemView.findViewById<TextView>(R.id.tv_bookstore_img_null)

    fun bind(data: MapData) {
        if (data.mainImg != null) {
            Glide.with(itemView).load(data.mainImg).into(img1)
            textNull.visibility = View.GONE
        } else {
            Glide.with(itemView).load(R.drawable.img_null).into(img1)
            textNull.visibility = View.VISIBLE
        }

        bookstoreName.text = data.bookstoreName
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
        itemView.setOnClickListener { itemClick(data, itemView) }
    }


}