package com.example.cozy.views.main.event

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import kotlinx.android.synthetic.main.activity_event.view.*

class EventViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    var iv_img : ImageView = itemView.findViewById(R.id.event_rv_img)

    fun bind(data: EventData){
        Glide.with(itemView).load(data.event_image).into(iv_img)
    }
}