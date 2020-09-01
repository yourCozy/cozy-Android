package com.example.cozy

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)  // custom하기 위해
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)   // 뒤로가기 버튼
        main_toolbar.elevation = 5F

        main_viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        main_viewPager.offscreenPageLimit = 2
        main_viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // 네비게이션 메뉴 아이템 체크
                navigation.menu.getItem(position).isChecked = true

            }

        })

        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_main -> main_viewPager.currentItem = 0
                R.id.menu_map -> main_viewPager.currentItem = 1
                R.id.menu_category -> main_viewPager.currentItem = 2
                R.id.menu_mypage -> main_viewPager.currentItem = 3
            }
            true
        }

        //카카오 해시키 가져오기
        getAppKeyHash()

    }

    private fun getAppKeyHash() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.d("Hash key", something)
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString())
        }

    }

}
