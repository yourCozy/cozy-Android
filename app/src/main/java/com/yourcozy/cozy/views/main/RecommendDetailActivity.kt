package com.yourcozy.cozy.views.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.yourcozy.cozy.DialogBookmark
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.responseData.BookstoreDetailData
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_recommend_detail.*
import kotlin.properties.Delegates

class RecommendDetailActivity : AppCompatActivity() {

    lateinit var tel: String
    var latitude by Delegates.notNull<Double>()
    var longitude by Delegates.notNull<Double>()
    lateinit var bookstoreName: String
    val service = RequestToServer.service
    lateinit var detailData : BookstoreDetailData
    var bookstoreIdx by Delegates.notNull<Int>()
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
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
        editor = sharedPref.edit()

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
            if(tel == "0") {
                Toast.makeText(this,"전화가 없어요.",Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$tel")
                startActivity(intent)
            }
        }

        save.setOnClickListener {
            val sharedPref = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            val header = mutableMapOf<String, String>()
            header["Content-Type"] = "application/json"
            header["token"] = sharedPref.getString("token", "token").toString()
            if(header["token"] == "token"){
                Toast.makeText(this,"로그인 후 이용해 주세요.",Toast.LENGTH_SHORT).show()
            }else {
                if (!save.isSelected) {
                    service.requestBookmarkUpdate(bookstoreIdx, header).customEnqueue(
                        onError = { Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT) },
                        onSuccess = {
                            Log.d("Bookmark message", "${it.body()!!.message}")
                            Log.d("Bookmark checked11", "${it.body()!!.data}")
                            if (it.body()!!.success) {
                                val data = it.body()!!.data
                                Log.d("Bookmark checked22", "북마크 성공 ${data!!.checked}")
                                save.isSelected = true
                                val inflater: LayoutInflater =
                                    this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                                val layout = inflater.inflate(R.layout.bookmark_custom_toast, null)

                                with(Toast(this)) {
                                    setGravity(Gravity.CENTER, 0, 0)
                                    duration = Toast.LENGTH_SHORT
                                    view = layout
                                    show()
                                }
                            }
                        })
                } else {
                    val customDialog = DialogBookmark(this)
                    customDialog.setOnOKClickedListener {
                        RequestToServer.service.requestBookmarkUpdate(bookstoreIdx, header)
                            .customEnqueue(
                                onError = {
                                    Toast.makeText(
                                        this,
                                        "올바르지 않은 요청입니다.",
                                        Toast.LENGTH_SHORT
                                    )
                                },
                                onSuccess = {
                                    Log.d("Bookmark message", "${it.body()!!.message}")
                                    Log.d("Bookmark checked11", "${it.body()!!.data}")
                                    if (it.body()!!.success) {
                                        save.isSelected = false
                                        Log.d("Bookmark checked22", "북마크 해제${it.body()!!.data!!.checked}")
                                    }
                                }
                            )
                    }
                    customDialog.start()
                }
            }
        }

        road.setOnClickListener {
            if(isInstalledApp(kakaoPackageName)) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://look?p=$latitude,$longitude"))
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://search?q=$bookstoreName&p=$latitude,$longitude"))
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
        val token = sharedPref.getString("token","token").toString()
        val cookie = sharedPref.getString("Cookie","cookie").toString()
        if (token != "token"){
            header["token"] = token
        }
        if(cookie != "cookie") {
            header["Cookie"] = cookie
        }
        service.requestBookstoreDatail(header,bookstoreIdx).customEnqueue(
            onError = { Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT) },
            onSuccess = {
                val cookie = it.headers().get("Set-Cookie")
                val idx: Int = cookie!!.indexOf(";")
                val Cookie = cookie.substring(0,idx)
                editor.putString("Cookie",Cookie)
                editor.commit()
                if (it.body()!!.success) {
                    detailData = it.body()!!.data.elementAt(0)
                    if (detailData.mainImg != null) {
                        Glide.with(this).load(detailData.mainImg).into(rec_de_img)
                        tv_rec_de_img_null.visibility = View.GONE
                    } else {
                        tv_rec_de_img_null.visibility = View.VISIBLE
                    }
                    if (detailData.profileImg != null) {
                        Glide.with(this).load(detailData.profileImg).into(bookstore_profile)
                    }else{
                        Glide.with(this).load(R.mipmap.ic_cozy).into(bookstore_profile)
                    }
                    bookstore_name.text = detailData.bookstoreName
                    bookstoreName = detailData.bookstoreName
                    if(detailData.hashtag1 != null) {
                        rec_de_tag1.text = "#" + detailData.hashtag1
                    }
                    else{
                        rec_de_tag1.visibility = View.INVISIBLE
                    }
                    if(detailData.hashtag2 != null) {
                        rec_de_tag2.text = "#" + detailData.hashtag2
                    }
                    else {
                        rec_de_tag2.visibility = View.INVISIBLE
                    }
                    if(detailData.hashtag3 != null) {
                        rec_de_tag3.text = "#" + detailData.hashtag3
                    }
                    else{
                        rec_de_tag3.visibility = View.INVISIBLE
                    }
                    rec_de_intro.text = detailData.notice
                    if(detailData.tel != null) {
                        tel = detailData.tel
                    }
                    else{
                        tel = "0"
                    }
                    save.isSelected = detailData.checked != 0
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
