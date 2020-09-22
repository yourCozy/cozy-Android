package com.example.cozy.views.category

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import kotlin.properties.Delegates

class CategoryViewHolder(
    itemView: View,
    val itemClick: (CategoryData, View) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    var activityIdx by Delegates.notNull<Int>()
    var bookstoreName: TextView = itemView.findViewById(R.id.tv_activity_bookstore_name)
    var activityName : TextView = itemView.findViewById(R.id.tv_activity_title)
    var image: ImageView = itemView.findViewById(R.id.iv_activity_intro)
    var price: TextView = itemView.findViewById(R.id.tv_activity_price)
    var dDay: TextView = itemView.findViewById(R.id.tv_activity_duedate)
    var textNull : TextView = itemView.findViewById(R.id.tv_activity_null)

    fun bind(categoryData: CategoryData) {
        if(categoryData.image1 != null){
            Glide.with(itemView).load(categoryData.image1).into(image)
            textNull.visibility = View.GONE
        }else{
            Glide.with(itemView).load(R.drawable.img_null).into(image)
            textNull.visibility = View.VISIBLE
        }
        activityIdx = categoryData.activityIdx
        activityName.text = categoryData.activityName
        bookstoreName.text = categoryData.bookstoreName
        if(categoryData.dday != null) {
            if (categoryData.dday == 0) {
                dDay.text = "오늘 마감"
            } else {
                dDay.text = "D-" + categoryData.dday
            }
        }
        else{
            dDay.text = "선착순"
        }
        price.text = categoryData.price

        itemView.setOnClickListener { itemClick(categoryData, itemView) }
    }

}