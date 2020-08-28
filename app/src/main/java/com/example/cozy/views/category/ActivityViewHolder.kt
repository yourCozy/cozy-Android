package com.example.cozy.views.category

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import kotlinx.android.synthetic.main.item_activity_intro.view.*

class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var bookstoreIdx: TextView = itemView.findViewById(R.id.tv_activity_bookstore_name)
    var activity_rv_bookstoreName: TextView = itemView.findViewById(R.id.tv_activity_title)
//    var categoryIdx: TextView = itemView.findViewById(R.id.)
//    var date: TextView = itemView.findViewById(R.id.tv_a)
    var deadline: TextView = itemView.findViewById(R.id.tv_activity_duedate)

    fun bind(activityData: ActivityData) {

    }

}