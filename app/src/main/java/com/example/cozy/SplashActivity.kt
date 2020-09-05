package com.example.cozy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {

    val SPLASH_VIEW_TIME: Long = 1000 //2초간 스플래시 화면을 보여줌 (ms)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({ //delay를 위한 handler
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }, SPLASH_VIEW_TIME)
    }
}