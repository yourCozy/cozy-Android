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
                    noticeTitle = "출시 안내",
                    noticeDate = "20.09.22",
                    noticeContent = "안녕하세요. 독립서점 소개 어플리케이션 코지입니다. 드디어 어플리케이션이 출시 되었어요~ 독립 서점에 대한 정보들이 지속적으로 업데이트 될 예정이니 관심 가지고 지켜봐주세요! 여러분들의 행복한 독서 생활"
                )
            )
            add(
                NoticeData(
                    noticeIdx = 2,
                    noticeTitle = "회원탈퇴 안내",
                    noticeDate = "20.09.09",
                    noticeContent = "죄송하지만 아직 기능 개발중이라 앱 내에서 탈퇴할 수 없습니다."
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