package com.example.cozy.views.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.cozy.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_recommend_detail.*

class RecommendDetailActivity : AppCompatActivity() {

    lateinit var tel: String
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

        detail_viewpager.adapter = TabViewPagerAdapter(supportFragmentManager)
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
            intent.data = Uri.parse("tel:0325242889")
            startActivity(intent)
        }

        save.setOnClickListener{
            val inflater : LayoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.bookmark_custom_toast,null)

            with (Toast(this)) {
                setGravity(Gravity.CENTER, 0, 0)
                duration = Toast.LENGTH_SHORT
                view = layout
                show()
            }

            if (save.isSelected){
                save.isSelected = false
            }
            else{
                save.isSelected = true
            }
        }

        road.setOnClickListener {
            if(isInstalledApp(kakaoPackageName)) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://look?p=37.549399,126.975219"))
                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$kakaoPackageName"))
                startActivity(intent)
            }
        }

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
