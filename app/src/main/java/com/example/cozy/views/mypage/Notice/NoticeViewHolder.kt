package com.example.cozy.views.mypage.Notice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.R
import com.example.cozy.views.mypage.Notice.NoticeData
import kotlin.properties.Delegates

class NoticeViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var noticeIdx by Delegates.notNull<Int>()
    var noticeTitle = itemView.findViewById<TextView>(R.id.tv_notice_title)
    var noticeDate = itemView.findViewById<TextView>(R.id.tv_notice_date)
    var noticeContent = itemView.findViewById<TextView>(R.id.tv_notice_content)

    fun bind(myData: NoticeData) {
        noticeIdx = myData.noticeIdx
        noticeTitle.text = myData.noticeTitle
        noticeDate.text = myData.noticeDate
        noticeContent.text = myData.noticeContent

    }
}