package com.example.cozy.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.cozy.LoginActivity
import com.example.cozy.R
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

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
                        onboarding_progressbar.progress = 25
                        go_select.visibility = View.INVISIBLE
                    }
                    1 -> {
                        onboarding_progressbar.progress = 50
                        go_select.visibility = View.INVISIBLE
                    }
                    2 -> {
                        onboarding_progressbar.progress = 75
                        go_select.visibility = View.INVISIBLE
                    }
                    else -> {
                        onboarding_progressbar.progress = 100
                        go_select.visibility = View.VISIBLE }
                }
            }
        })
        onboarding_viewpager.setPageTransformer(true, object: ViewPager.PageTransformer{
            override fun transformPage(view: View, position: Float) {
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
        })

        go_select.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}