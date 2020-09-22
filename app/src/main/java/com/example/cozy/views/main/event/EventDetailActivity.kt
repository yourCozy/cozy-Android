package com.example.cozy.views.main.event

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.network.requestData.RequestCommentWrite
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
    var commentIdx by Delegates.notNull<Int>()
    var commentData = mutableListOf<CommentData>()
    private lateinit var detailCommentData : CommentData
    lateinit var imm : InputMethodManager
    lateinit var commentAdapter: CommentAdapter
    lateinit var sharedPref: SharedPreferences
    var isNew = true
    var imageList = mutableListOf<String>()

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

        sharedPref = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        if(sharedPref.getString("token", "token") == "token"){
            write_comment.visibility = View.GONE
        }

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        event_bookmark.visibility = View.INVISIBLE
        yellowbtn.visibility = View.GONE
        yellowbtn_text.visibility = View.GONE

        loadData()
        loadComment()
        putComment()

    }



    private fun loadData() {
        val header = mutableMapOf<String, String?>()
        header["Context-Type"] = "application/json"
        header["token"] = sharedPref.getString("token", "token")
        if(header["token"] == "token"){
            service.requestEventDetail(activityIdx).customEnqueue(
                onError = {Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)},
                onSuccess = {
                    if(it.body()!!.success){
                        detailData = it.body()!!.data.elementAt(0)
                        if(detailData.image1 == null){
                            Glide.with(this).load(R.drawable.img_null).into(event_img)
                            eventdetail_img_prepare.visibility = View.VISIBLE
                        }else {
                            Glide.with(this).load(detailData.image1).into(event_img)
                            eventdetail_img_prepare.visibility = View.GONE
                        }

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


                        for(i in arrayImage){
                            if(i != null){
                                imageList.add(i)
                            }
                            else break
                        }
                        Log.d("길이!",imageList.toString())
                        if(imageList.size == 0)
                            rc_event.visibility = View.GONE

                        eventdetailAdapter = EventDetailAdapter(this)
                        rc_event.adapter = eventdetailAdapter
                        eventdetailAdapter.data = imageList
                        eventdetailAdapter.notifyDataSetChanged()
                        event_tv_cate.text = detailData.categoryName
                        event_tv_name.text = detailData.activityName

                        if (detailData.dday != null) {

                            when (detailData.dday) {
                                0 -> {
                                    event_tv_day.text = "오늘 마감"
                                }
                                else -> {
                                    event_tv_day.text = "D-" + detailData.dday
                                }
                            }
                        }
                        else{
                            event_tv_day.text = "선착순"
                        }
                        event_tv_time_explain.text = detailData.period

                        if(SimpleDateFormat("yyyy").format(detailData.deadline).toString().equals("3000")){
                            event_tv_deadline_explain.text = "선착순 마감"
                        }else{
                            event_tv_deadline_explain.text = SimpleDateFormat("yyyy.MM.dd").format(detailData.deadline)
                        }
                        if(detailData.limitation == null){
                            event_tv_people_explain.text = "제한없음"
                        }
                        else {
                            event_tv_people_explain.text = detailData.limitation.toString() + "명"
                        }
                        if(detailData.price == 0){
                            event_tv_cost_explain.text = "무료"
                        }
                        else{
                            event_tv_cost_explain.text = detailData.price.toString() + "원"
                        }
                        event_tv_introduce.text = detailData.introduction

                        Glide.with(this).load(R.mipmap.ic_cozy).into(event_comment_user)

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
                    if (it.body()!!.success) {
                        if (it.body()!!.success) {
                            detailData = it.body()!!.data.elementAt(0)
                            if(detailData.image1 == null){
                                Glide.with(this).load(R.drawable.img_null).into(event_img)
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


                            for(i in arrayImage){
                                if(i != null){
                                    imageList.add(i)
                                }
                                else break
                            }
                            Log.d("길이!",imageList.toString())
                            if(imageList.size == 0)
                                rc_event.visibility = View.GONE

                            eventdetailAdapter = EventDetailAdapter(this)
                            rc_event.adapter = eventdetailAdapter
                            eventdetailAdapter.data = imageList
                            eventdetailAdapter.notifyDataSetChanged()
                            event_tv_cate.text = detailData.categoryName
                            event_tv_name.text = detailData.activityName
                            if (detailData.dday != null) {

                                when (detailData.dday) {
                                    0 -> {
                                        event_tv_day.text = "오늘 마감"
                                    }
                                    else -> {
                                        event_tv_day.text = "D-" + detailData.dday
                                    }
                                }
                            }
                            else{
                                event_tv_day.text = "선착순"
                            }
                            event_tv_time_explain.text = detailData.period
                            if(SimpleDateFormat("yyyy").format(detailData.deadline).toString().equals("3000")){
                                event_tv_deadline_explain.text = "선착순 마감"
                            }else{
                                event_tv_deadline_explain.text = SimpleDateFormat("yyyy.MM.dd").format(detailData.deadline)
                            }
                            if (detailData.limitation == null) {
                                event_tv_people_explain.text = "제한없음"
                            } else {
                                event_tv_people_explain.text =
                                    detailData.limitation.toString() + "명"
                            }
                            if (detailData.price == 0) {
                                event_tv_cost_explain.text = "무료"
                            } else {
                                event_tv_cost_explain.text = detailData.price.toString() + "원"
                            }
                            event_tv_introduce.text = detailData.introduction

                            Glide.with(this).load(R.mipmap.ic_cozy).into(event_comment_user)
                        }

                    }
                }
            )
        }
        rc_event.addItemDecoration(ItemDecoration(this, 8,0))
    }

    override fun onResume() {
        super.onResume()
        loadComment()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.search,menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    private fun loadComment(){
        val header = mutableMapOf<String, String?>()
        header["Context-Type"] = "application/json"
        val token = sharedPref.getString("token", "token")
        if(token != "token"){
            header["token"] = sharedPref.getString("token", "token")
        }
        service.requestComment(activityIdx,header).customEnqueue(
            onError = { Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT) },
            onSuccess = {
                if(it.body()!!.success){
                    detailCommentData = it.body()!!.data.elementAt(0)
                    commentAdapter = CommentAdapter(this) { mycommentIdx: Int, comment_text: String ->
                        event_comment_write.setText(comment_text)
                        event_comment_write.requestFocus()   // 포커스 주기
                        event_comment_write.setSelection(event_comment_write.length())
                        imm.showSoftInput(event_comment_write, InputMethodManager.SHOW_IMPLICIT)   // 키보드 띄우기
                        isNew = false
                        commentIdx = mycommentIdx
                        }

                    rv_comment.adapter = commentAdapter
                    commentData.clear()
                    commentData.addAll(it.body()!!.data)
                    commentAdapter.data = commentData
                    commentAdapter.notifyDataSetChanged()
                }
            }

        )
    }

    private fun putComment(){

        findViewById<EditText>(R.id.event_comment_write).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.isEmpty()) {
                    comment_button.isSelected = false
                    comment_btn_text.setTextColor(resources.getColor(R.color.disabled))
                } else{
                    comment_button.isSelected = true
                    comment_btn_text.setTextColor(resources.getColor(R.color.white))
                }
            }
        })

        comment_button.setOnClickListener {
            if (comment_button.isSelected) {
                Log.d("텍스트 내용", event_comment_write.text.toString())
                val sharedPref = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
                val header = mutableMapOf<String, String?>()
                header["Context-Type"] = "application/json"
                header["token"] = sharedPref.getString("token", "token")
                if(isNew) {
                    service.requestCommentWrite(
                        RequestCommentWrite(content = event_comment_write.text.toString()), header, activityIdx
                    ).customEnqueue(
                        onError = { Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)
                        },
                        onSuccess = {
                            if(it.body()!!.success) {
                                Log.d("댓글 수정 완료 여부", it.body()!!.message)
                                Toast.makeText(this, "댓글 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                event_comment_write.text = null
                                imm.hideSoftInputFromWindow(getCurrentFocus()!!.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
                                //다시 불러오기
                                loadComment()
                            }else{
                                Log.d("댓글 작성 실패", it.body()!!.message)
                            }
                        }
                    )
                }
                else{
                    service.requestCommentChange(commentIdx,RequestCommentWrite(content = event_comment_write.text.toString()),header).customEnqueue(
                        onError = { Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)
                        },
                        onSuccess = {
                            if(it.body()!!.success) {
                                Log.d("댓글 수정 완료 여부", it.body()!!.message)
                                Toast.makeText(this, "댓글 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                event_comment_write.text = null
                                imm.hideSoftInputFromWindow(getCurrentFocus()!!.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
                                isNew = true
                                //다시 불러오기
                                loadComment()
                            }else{
                                Log.d("댓글 수정 실패", it.body()!!.message)
                            }
                        }
                    )
                }
            }
            else{
                Toast.makeText(this, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.search -> Toast.makeText(this,"검색", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}