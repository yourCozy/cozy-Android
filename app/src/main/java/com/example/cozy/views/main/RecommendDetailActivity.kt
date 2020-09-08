package com.example.cozy.views.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.network.responseData.BookstoreDetailData
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_recommend_detail.*
import kotlin.properties.Delegates

class RecommendDetailActivity : AppCompatActivity() {

    lateinit var tel: String
    var latitude by Delegates.notNull<Double>()
    var longitude by Delegates.notNull<Double>()
    val service = RequestToServer.service
    lateinit var detailData : BookstoreDetailData
    var bookstoreIdx by Delegates.notNull<Int>()
    lateinit var sharedPref: SharedPreferences
    var kakaoPackageName : String = "net.daum.android.map"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_detail)

        setSupportActionBar(rec_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        rec_toolbar.elevation = 5F

        if (intent.hasExtra("bookstoreIdx")) {
            bookstoreIdx = intent.getIntExtra("bookstoreIdx",0)
        }
        sharedPref = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)

        initDetail()

        detail_viewpager.adapter = TabViewPagerAdapter(supportFragmentManager,bookstoreIdx)
        detail_viewpager.offscreenPageLimit = 2
        detail_tablayout.setupWithViewPager(detail_viewpager)
        detail_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(detail_tablayout))
        detail_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {}
        })

        call.setOnClickListener {
            val intent  = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$tel")
            startActivity(intent)
        }

        save.setOnClickListener{
            val header = mutableMapOf<String, String>()
            header["Content-Type"] = "application/json"
            header["token"] = sharedPref.getString("token","token").toString()
            service.requestBookmarkUpdate(bookstoreIdx,header).customEnqueue(
                onError = {Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT) },
                onSuccess = {
                    Log.d("Bookmark message", "${it.message}")
                    if(it.success){
                        val data = it.data
                        if (data!!.checked == 1){
                            Log.d("Bookmark message", "북마크 성공 ${it.message}")
                            save.isSelected = true
                            val inflater : LayoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                            val layout = inflater.inflate(R.layout.bookmark_custom_toast,null)

                            with (Toast(this)) {
                                setGravity(Gravity.CENTER, 0, 0)
                                duration = Toast.LENGTH_SHORT
                                view = layout
                                show()
                            }
                        }
                        else{
                            save.isSelected = false
                            Log.d("Bookmark message", "북마트 해제 ${it.message}")
                        }
                    }
                }
            )
        }

        road.setOnClickListener {
            if(isInstalledApp(kakaoPackageName)) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://look?p=$latitude,$longitude"))
                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$kakaoPackageName"))
                startActivity(intent)
            }
        }

    }

    private fun initDetail() {

        val header = mutableMapOf<String, String>()
        header["Content-Type"] = "application/json"
//        header["token"] = sharedPref.getString("token","token").toString()
        header["token"] = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4Ijo4LCJpYXQiOjE1OTk1NDUwODMsImV4cCI6MTU5OTU4MTA4MywiaXNzIjoib3VyLXNvcHQifQ.5LiwFhnFJ-zLcuafwaGzHtjdlxIlM13sXgXdnb_G7q8"
        service.requestBookstoreDatail(header,bookstoreIdx).customEnqueue(
            onError = {Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)},
            onSuccess = {
                if(it.success){
                    detailData = it.data.elementAt(0)
                    Glide.with(this).load(detailData.mainImg).into(rec_de_img)
                    Glide.with(this).load(detailData.profileImg).into(bookstore_profile)
                    bookstore_name.text = detailData.bookstoreName
                    rec_de_tag1.text = detailData.hashtag1
                    rec_de_tag2.text = detailData.hashtag2
                    rec_de_tag3.text = detailData.hashtag3
                    rec_de_intro.text = detailData.description
                    tel = detailData.tel
                    save.isSelected = detailData.bookmark != 0
                    latitude = detailData.latitude
                    longitude = detailData.longitude
                    rec_de_adress.text = detailData.location
                    rec_de_time.text = detailData.businessHours
                    rec_de_closed.text = detailData.dayoff
                    rec_de_activity.text = detailData.activities

                }
            }
        )
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.search,menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.search -> Toast.makeText(this,"검색",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun isInstalledApp(packageName : String): Boolean {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        return intent != null
    }
}
