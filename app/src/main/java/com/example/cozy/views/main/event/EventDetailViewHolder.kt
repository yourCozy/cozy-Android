package com.example.cozy.views.main.event

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cozy.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class EventDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var activityIdx by Delegates.notNull<Int>()
    var activityName: TextView = itemView.findViewById(R.id.event_tv_name)
    var bookstoreIdx by Delegates.notNull<Int>()
    var categoryIdx by Delegates.notNull<Int>()
    var categoryName: TextView = itemView.findViewById(R.id.event_tv_cate)
    var price: TextView = itemView.findViewById(R.id.event_tv_cost_explain)
    var limitation: TextView = itemView.findViewById(R.id.event_tv_people_explain) //null이면 제한 없음으로
    var introduction: TextView = itemView.findViewById(R.id.event_tv_introduce)
    var period: TextView = itemView.findViewById(R.id.event_tv_time_explain)
    var deadline: TextView = itemView.findViewById(R.id.event_tv_deadline_explain) //날짜만 보여주기
    var image1: ImageView = itemView.findViewById(R.id.rc_event)
    var image2: ImageView = itemView.findViewById(R.id.rc_event)
    var image3: ImageView = itemView.findViewById(R.id.rc_event)
    var image4: ImageView = itemView.findViewById(R.id.rc_event)
    var image5: ImageView = itemView.findViewById(R.id.rc_event)
    var image6: ImageView = itemView.findViewById(R.id.rc_event)
    var image7: ImageView = itemView.findViewById(R.id.rc_event)
    var image8: ImageView = itemView.findViewById(R.id.rc_event)
    var image9: ImageView = itemView.findViewById(R.id.rc_event)
    var image10: ImageView = itemView.findViewById(R.id.rc_event)
    val dday: TextView = itemView.findViewById(R.id.event_tv_day)
    val event_rv_img: ImageView = itemView.findViewById(R.id.event_rv_img)

    fun bind(data: EventDetailData) {
        activityIdx = data.activityIdx
        activityName.text = data.activityName
        bookstoreIdx = data.bookstoreIdx
        categoryIdx = data.categoryIdx
        categoryName.text = data.categoryName
        if (data.price == 0) {
            price.text = "무료"
        } else {
            price.text = "" + data.price + "원"
        }
        if (limitation == null) {
            limitation.text = "제한없음"
        } else {
            limitation.text = "" + data.limitation + "명"
        }
        introduction.text = data.introduction
        period.text = data.period
        deadline.text = SimpleDateFormat("yyyy.MM.dd").format(data.deadline)

        dday.text = "D-" + data.dday
        val arr = validImage(data)
        for(i in 0..arr.size){
            Glide.with(itemView).load(arr[i]).into(event_rv_img)
        }



//        Glide.with(itemView).load(data.img).into(event_rv_img)
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