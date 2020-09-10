package com.example.cozy.views.mypage.Notice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.R
import com.example.cozy.views.mypage.MypageFragment
import kotlinx.android.synthetic.main.activity_notice.*

class NoticeActivitiy : AppCompatActivity() {
    lateinit var noticeAdapter: NoticeAdapter
    var data = mutableListOf<NoticeData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        setSupportActionBar(tb_notice)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)

        tb_notice.elevation = 5F


        noticeAdapter =
            NoticeAdapter(tb_notice.context)

        with(rv_notice) {
            adapter = noticeAdapter
        }

        loadData()
    }

    fun loadData() {
        data.apply {
            add(
                NoticeData(
                    noticeIdx = 1,
                    noticeTitle = "버그 안내",
                    noticeDate = "20.09.09",
                    noticeContent = "안드로이드 sdk 26버전 이하에서 발생하는 버그를 발견하여 조치를 취하였습니다. 앞으로도 열심히 개발하겠다"
                )
            )
            add(
                NoticeData(
                    noticeIdx = 2,
                    noticeTitle = "회원탈퇴 안내",
                    noticeDate = "20.09.09",
                    noticeContent = "죄송하지만 아직 기능이 개발중이라 탈퇴는 할 수 없습니다. ㅎㅎ"
                )
            )
        }
        noticeAdapter.data = data
        noticeAdapter.notifyDataSetChanged()
        Log.d("CHECK_FUN", "load Data() 작동함.")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(applicationContext, MypageFragment::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}