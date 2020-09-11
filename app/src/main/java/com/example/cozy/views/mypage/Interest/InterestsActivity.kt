package com.example.cozy.views.mypage.Interest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.views.main.RecommendDetailActivity
import kotlinx.android.synthetic.main.activity_interests.*

class InterestsActivity : AppCompatActivity() {
    private val service = RequestToServer.service
    lateinit var interestAdapter: InterestsAdapter
    var data = mutableListOf<InterestsData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        setSupportActionBar(tb_interests)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)

        tb_interests.elevation = 5F

        loadInterestData()
        rv_interests.addItemDecoration(ItemDecoration(this, 0,32))
    }


    override fun onResume() {
        super.onResume()
        loadInterestData()
    }

    //관심 책방 불러오기
    private fun loadInterestData(){
        val sharedPref = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val header = mutableMapOf<String, String?>()
        val token = sharedPref.getString("token", "token")
        header["Context-Type"] = "application/json"
        header["token"] = token
        service.requestInterest(header).customEnqueue(
            onError = { Log.d("test", "error")},
            onSuccess = {
                if (it.success) {
                    if (it.message == "서점 리스트 조회 성공") {
                        interestAdapter =
                            InterestsAdapter(
                                this,
                                it.data.toMutableList(),
                                { onEmpty() }) { InterestsData, View ->
                                val intent = Intent(this, RecommendDetailActivity::class.java)
                                startActivity(intent)
                            }
                        rv_interests.adapter = interestAdapter
                        rv_interests.visibility = View.VISIBLE
                        no_interest.visibility = View.GONE
                    }else onEmpty()
                }else onEmpty()
            })
    }

    fun loadData() {
        data.apply {
            add(
                InterestsData(
                    bookstoreIdx = 1,
                    mainimg = "",
                    bookstoreName = "코지책방",
                    shortIntro1 = "매일 새로 구운 빵과 함께하는 달콤한 책\n그리고 오늘 봄날의 책방",
                    shortIntro2 = "매일 새로 구운 빵과 함께하는",
                    location = "서울특병시 용산구 한강대로 102길 5",
                    tag1 = "베이커리",
                    tag2 = "전시",
                    tag3 = "영화상영"

                )
            )
            add(
                InterestsData(
                    bookstoreIdx = 1,
                    mainimg = "",
                    bookstoreName = "콩 볶는 책방",
                    shortIntro1 = "매일 새로 볶은 커피와 함께하는 향긋한 독서\n콩 볶는 책방",
                    shortIntro2 = "매일 새로 구운 빵과 함께하는",
                    location = "서울특병시 용산구 한강대로 102길 5",
                    tag1 = "커피",
                    tag2 = "전시",
                    tag3 = "영화상영"

                )
            )
        }
        interestAdapter.data = data
        rv_interests.addItemDecoration(ItemDecoration(this, 0, 32))
        interestAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onEmpty() {
        rv_interests.visibility = View.GONE
        no_interest.visibility = View.VISIBLE
    }
}