package com.example.cozy.views.category

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R
import kotlin.properties.Delegates

class CategoryViewHolder(
    itemView: View,
    val itemClick: (CategoryData, View) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    var activityIdx by Delegates.notNull<Int>()
    var bookstoreName: TextView = itemView.findViewById(R.id.tv_activity_bookstore_name)
    var activityName: TextView = itemView.findViewById(R.id.tv_activity_title)
    var image: ImageView = itemView.findViewById(R.id.iv_activity_intro)
    var shortIntro by Delegates.notNull<String>() //이게 뭐하는 데이터인지 물어볼 것
    var price: TextView = itemView.findViewById(R.id.tv_activity_price)
    var dDay: TextView = itemView.findViewById(R.id.tv_activity_duedate)

    fun bind(categoryData: CategoryData) {
//        profile.setImageResource(R.drawable.ex_img_activity_unsplash)
        if(categoryData.image != ""){
            val resourceId = itemView.context.resources.getIdentifier(categoryData.image, "drawable", itemView.context.packageName)
            image.setImageResource(resourceId)
        }else{
            image.setImageResource(R.drawable.ex_img_activity_unsplash)
        }
        activityIdx = categoryData.activityIdx
        activityName.text = categoryData.activityName
        bookstoreName.text = categoryData.bookstoreName
        shortIntro = categoryData.shortIntro
        dDay.text = "D-" + categoryData.dday
        price.text = categoryData.price

        itemView.setOnClickListener { itemClick(categoryData, itemView) }
    }

}