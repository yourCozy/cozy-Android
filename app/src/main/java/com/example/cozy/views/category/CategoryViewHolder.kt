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
    var activityName by Delegates.notNull<String>()
    var image: ImageView = itemView.findViewById(R.id.iv_activity_intro)
    var shortIntro : TextView = itemView.findViewById(R.id.tv_activity_title) //이게 뭐하는 데이터인지 물어볼 것
    var price: TextView = itemView.findViewById(R.id.tv_activity_price)
    var dDay: TextView = itemView.findViewById(R.id.tv_activity_duedate)

    fun bind(categoryData: CategoryData) {
//        profile.setImageResource(R.drawable.ex_img_activity_unsplash)
        if(categoryData.image != ""){
            Glide.with(itemView).load(categoryData.image).into(image)
        }else{
            image.setImageResource(R.drawable.ex_img_activity_unsplash)
        }
        activityIdx = categoryData.activityIdx
        activityName = categoryData.activityName
        bookstoreName.text = categoryData.bookstoreName
        shortIntro.text = categoryData.shortIntro
        dDay.text = "D-" + categoryData.dday
        price.text = categoryData.price

        itemView.setOnClickListener { itemClick(categoryData, itemView) }
    }

}