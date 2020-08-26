package com.example.cozy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

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


    }
}
