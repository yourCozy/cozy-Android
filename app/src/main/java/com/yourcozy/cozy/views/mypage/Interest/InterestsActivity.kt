package com.yourcozy.cozy.views.mypage.Interest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yourcozy.cozy.ItemDecoration
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.views.main.RecommendDetailActivity
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
                if (it.body()!!.success) {
                    if (it.body()!!.message == "서점 리스트 조회 성공") {
                        interestAdapter =
                            InterestsAdapter(
                                this,
                                it.body()!!.data.toMutableList(),
                                { onEmpty() }) { InterestsData, View ->
                                val intent = Intent(this, RecommendDetailActivity::class.java)
                                intent.putExtra("bookstoreIdx", InterestsData.bookstoreIdx)
                                startActivity(intent)
                            }
                        rv_interests.adapter = interestAdapter
                        rv_interests.visibility = View.VISIBLE
                        no_interest.visibility = View.GONE
                    }else onEmpty()
                }else onEmpty()
            })
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