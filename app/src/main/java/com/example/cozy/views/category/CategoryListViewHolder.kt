package com.example.cozy.views.category

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R
import kotlin.properties.Delegates

class CategoryListViewHolder(
    itemView: View,
    val itemClick: (CategoryData, View) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    var bookstoreIdx by Delegates.notNull<Int>()
    var profile: ImageView = itemView.findViewById(R.id.iv_activity_intro)
    var eventTitle: TextView = itemView.findViewById(R.id.tv_activity_title)
    var bookstoreName: TextView = itemView.findViewById(R.id.tv_activity_bookstore_name)
    var categoryIdx by Delegates.notNull<String>()
    var price: TextView = itemView.findViewById(R.id.tv_activity_price)
    var deadline: TextView = itemView.findViewById(R.id.tv_activity_duedate)

    fun bind(categoryData: CategoryData) {
//        profile.setImageResource(R.drawable.ex_img_activity_unsplash)
        if(categoryData.profile != ""){
            val resourceId = itemView.context.resources.getIdentifier(categoryData.profile, "drawable", itemView.context.packageName)
            profile.setImageResource(resourceId)
        }else{
            profile.setImageResource(R.drawable.ex_img_activity_unsplash)
        }
        bookstoreIdx = categoryData.bookstoreIdx

        eventTitle.text = categoryData.activity_name
        bookstoreName.text = categoryData.bookstore_name
        deadline.text = categoryData.deadline
        categoryIdx = categoryData.categoryIdx
        price.text = categoryData.price
        itemView.setOnClickListener { itemClick(categoryData, itemView) }
    }

}