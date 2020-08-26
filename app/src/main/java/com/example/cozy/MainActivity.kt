package com.example.cozy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        actionBar?.setDisplayShowCustomEnabled(true);
        actionBar?.setDisplayShowTitleEnabled(true);
        actionBar?.setDisplayHomeAsUpEnabled(true);


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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        return true
    }
}
