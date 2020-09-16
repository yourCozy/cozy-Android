package com.example.cozy.views.main.event

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import com.example.cozy.views.main.event.comment.CommentData


class EventDetailAdapter (private val context : Context, private var data : Array<String?>): RecyclerView.Adapter<EventDetailViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event_items, parent, false)
    return EventDetailViewHolder(
        view
    )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: EventDetailViewHolder, position: Int) {
        Glide.with(context).load(data[position]).into(holder.event_rv_img)

    }

    fun validImage(data: EventDetailData): Array<String?> {
        var cnt : Int = 0
        val arrayImage = arrayOf(
            data.image1,
            data.image2,
            data.image3,
            data.image4,
            data.image5,
            data.image6,
            data.image7,
            data.image8,
            data.image9,
            data.image10
        )
        for(i in 0..9){
            if(arrayImage[i] == null){
                cnt = i //까지만 valid한 데이터
                break
            }
        }
        val arrayValidImage = arrayImage.copyOfRange(0,cnt)

        return arrayValidImage;
    }
}