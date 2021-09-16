package com.yourcozy.cozy.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.yourcozy.cozy.signin.LoginActivity
import com.yourcozy.cozy.R
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kotlinx.android.synthetic.main.layout_information.*

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val sharedPreferences = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirst",false)
        editor.apply()
        editor.commit()

        iv_info_close.setOnClickListener {
            layoutinfo.visibility = View.INVISIBLE
        }

        onboarding_viewpager.adapter = OnBoardingAdapter(supportFragmentManager)
        onboarding_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
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
                when(position){
                    0 -> {
                        iv_one.setImageResource(R.drawable.icon_circle_white)
                        iv_two.setImageResource(R.drawable.icon_circle_gray)
                        iv_three.setImageResource(R.drawable.icon_circle_gray)
                        dotlayout.visibility = View.VISIBLE
                        go_select.visibility = View.INVISIBLE
                    }
                    1 -> {
                        iv_one.setImageResource(R.drawable.icon_circle_gray)
                        iv_two.setImageResource(R.drawable.icon_circle_white)
                        iv_three.setImageResource(R.drawable.icon_circle_gray)
                        dotlayout.visibility = View.VISIBLE
                        go_select.visibility = View.INVISIBLE
                    }
                    2 -> {
                        iv_one.setImageResource(R.drawable.icon_circle_gray)
                        iv_two.setImageResource(R.drawable.icon_circle_gray)
                        iv_three.setImageResource(R.drawable.icon_circle_white)
                        dotlayout.visibility = View.VISIBLE
                        go_select.visibility = View.INVISIBLE
                    }
                    else -> {
                        dotlayout.visibility = View.INVISIBLE
                        go_select.visibility = View.VISIBLE }
                }
            }
        })
        onboarding_viewpager.setPageTransformer(true
        ) { view, position ->
            if(position <= -1.0F || position >= 1.0F) {
                view.setTranslationX(view.getWidth() * position);
                view.setAlpha(0.0F);
            } else if( position == 0.0F ) {
                view.setTranslationX(view.getWidth() * position);
                view.setAlpha(1.0F);
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.setTranslationX(view.getWidth() * -position);
                view.setAlpha(1.0F - Math.abs(position));
            }
        }

        go_select.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}