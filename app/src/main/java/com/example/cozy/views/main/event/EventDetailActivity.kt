package com.example.cozy.views.main.event

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.views.main.event.comment.CommentAdapter
import com.example.cozy.views.main.event.comment.CommentData
import kotlinx.android.synthetic.main.activity_event_detail.*
import java.text.SimpleDateFormat
import kotlin.properties.Delegates

class EventDetailActivity : AppCompatActivity(){

    val service = RequestToServer.service
    lateinit var eventdetailAdapter : EventDetailAdapter
    var data = mutableListOf<EventDetailData>()
    private lateinit var detailData : EventDetailData
    var activityIdx by Delegates.notNull<Int>()
    private var commentData = mutableListOf<CommentData>()
    lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        setSupportActionBar(event_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        event_toolbar.elevation = 5F

        if (intent.hasExtra("activityIdx")) {
            activityIdx = intent.getIntExtra("activityIdx",0)
        }

        event_bookmark.visibility = View.INVISIBLE
        yellowbtn.visibility = View.GONE
        yellowbtn_text.visibility = View.GONE

        commentAdapter = CommentAdapter(this)
        rv_comment.adapter = commentAdapter

        loadComment()

        loadData()


    }



    fun loadData() {
        val sharedPref = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val header = mutableMapOf<String, String?>()
        header["Context-Type"] = "application/json"
        header["token"] = sharedPref.getString("token", "token")
        if(header["token"] == "token"){
            service.requestEventDetail(activityIdx).customEnqueue(
                onError = {Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)},
                onSuccess = {
                    if(it.success){
                        detailData = it.data.elementAt(0)
                        if(detailData.image1 == null){
                            Glide.with(this).load(R.drawable.null_img).into(event_img)
                            eventdetail_img_prepare.visibility = View.VISIBLE
                        }else {
                            Glide.with(this).load(detailData.image1).into(event_img)
                            eventdetail_img_prepare.visibility = View.GONE
                        }
                        //리사이클러뷰
                        val arrayImage = arrayOf(
                            detailData.image2,
                            detailData.image3,
                            detailData.image4,
                            detailData.image5,
                            detailData.image6,
                            detailData.image7,
                            detailData.image8,
                            detailData.image9,
                            detailData.image10
                        )


                        eventdetailAdapter = EventDetailAdapter(this, arrayImage)
                        rc_event.adapter = eventdetailAdapter
                        eventdetailAdapter.notifyDataSetChanged()
                        event_tv_cate.text = detailData.categoryName
                        event_tv_name.text = detailData.activityName
                        if(detailData.dday < 0){
                            event_tv_day.text = "D-" + detailData.dday
                        }
                        else if(detailData.dday == 0){
                            event_tv_day.text = "오늘 마감"
                        }
                        else{
                            event_tv_day.text = "마감"
                        }
                        event_tv_time_explain.text = detailData.period
                        event_tv_deadline_explain.text = SimpleDateFormat("yyyy.MM.dd").format(detailData.deadline)
                        if(detailData.limitation == null){
                            event_tv_people_explain.text = "제한없음"
                        }
                        else {
                            event_tv_people_explain.text = detailData.limitation.toString() + "명"
                        }
                        if(detailData.price == null){
                            event_tv_cost_explain.text = "무료"
                        }
                        else{
                            event_tv_cost_explain.text = detailData.price.toString() + "원"
                        }
                        event_tv_introduce.text = detailData.introduction


                    }
                }
            )
        }
        else {
            service.requestEventDetailLogin(activityIdx, header).customEnqueue(
                onError = {
                    Toast.makeText(
                        this,
                        "올바르지 않은 요청입니다.",
                        Toast.LENGTH_SHORT
                    )
                },
                onSuccess = {
                    if (it.success) {
                        if (it.success) {
                            detailData = it.data.elementAt(0)
                            if(detailData.image1 == null){
                                Glide.with(this).load(R.drawable.null_img).into(event_img)
                                eventdetail_img_prepare.visibility = View.VISIBLE
                            }else {
                                Glide.with(this).load(detailData.image1).into(event_img)
                                eventdetail_img_prepare.visibility = View.GONE
                            }
                            //리사이클러뷰
                            val arrayImage = arrayOf(
                                detailData.image2,
                                detailData.image3,
                                detailData.image4,
                                detailData.image5,
                                detailData.image6,
                                detailData.image7,
                                detailData.image8,
                                detailData.image9,
                                detailData.image10
                            )

                            eventdetailAdapter = EventDetailAdapter(this, arrayImage)
                            rc_event.adapter = eventdetailAdapter
                            eventdetailAdapter.notifyDataSetChanged()
                            event_tv_cate.text = detailData.categoryName
                            event_tv_name.text = detailData.activityName
                            if (detailData.dday < 0) {
                                event_tv_day.text = "D-" + detailData.dday
                            } else if (detailData.dday == 0) {
                                event_tv_day.text = "오늘 마감"
                            } else {
                                event_tv_day.text = "마감"
                            }
                            event_tv_time_explain.text = detailData.period
                            event_tv_deadline_explain.text =
                                SimpleDateFormat("yyyy.MM.dd").format(detailData.deadline)
                            if (detailData.limitation == null) {
                                event_tv_people_explain.text = "제한없음"
                            } else {
                                event_tv_people_explain.text =
                                    detailData.limitation.toString() + "명"
                            }
                            if (detailData.price == null) {
                                event_tv_cost_explain.text = "무료"
                            } else {
                                event_tv_cost_explain.text = detailData.price.toString() + "원"
                            }
                            event_tv_introduce.text = detailData.introduction

                        }

                    }
                }
            )
        }
        rc_event.addItemDecoration(ItemDecoration(this, 8,0))


    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.search,menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    private fun loadComment(){
        commentData.apply {
            add(
                CommentData(
                    activityIdx = 1,
                    commentProfile = "https://pixabay.com/ko/photos/%EA%B3%A0%EC%96%91%EC%9D%B4-%EB%8B%AC%EC%BD%A4%ED%95%9C-%ED%82%A4%ED%8B%B0-%EB%8F%99%EB%AC%BC-323262/",
                    commentName = "지윤김",
                    commentDate = "20.09.07  22:10",
                    commentText = "이 활동은 홍철책방에 다시 찾아온 감각적 전시입니다. 1만여 점의 작품 중 주목할 만한 작품을 올해 20주년을 맞아 전시를 진행하고 있습니다."
                )
            )
            add(
                CommentData(
                    activityIdx = 1,
                    commentProfile = "https://pixabay.com/ko/photos/%EA%B3%A0%EC%96%91%EC%9D%B4-%EB%8B%AC%EC%BD%A4%ED%95%9C-%ED%82%A4%ED%8B%B0-%EB%8F%99%EB%AC%BC-323262/",
                    commentName = "지윤김",
                    commentDate = "20.09.07  22:10",
                    commentText = "이 활동은 홍철책방에 다시 찾아온 감각적 전시입니다. 1만여 점의 작품 중 주목할 만한 작품을 올해 20주년을 맞아 전시를 진행하고 있습니다."
                )
            )
            add(
                CommentData(
                    activityIdx = 1,
                    commentProfile = "https://pixabay.com/ko/photos/%EA%B3%A0%EC%96%91%EC%9D%B4-%EB%8B%AC%EC%BD%A4%ED%95%9C-%ED%82%A4%ED%8B%B0-%EB%8F%99%EB%AC%BC-323262/",
                    commentName = "지윤김",
                    commentDate = "20.09.07  22:10",
                    commentText = "이 활동은 홍철책방에 다시 찾아온 감각적 전시입니다. 1만여 점의 작품 중 주목할 만한 작품을 올해 20주년을 맞아 전시를 진행하고 있습니다."
                )
            )
        }
        commentAdapter.data = commentData
        commentAdapter.notifyDataSetChanged()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.search -> Toast.makeText(this,"검색", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}