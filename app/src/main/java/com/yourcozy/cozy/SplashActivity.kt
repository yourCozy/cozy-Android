package com.yourcozy.cozy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yourcozy.cozy.onboarding.OnBoardingActivity
import com.yourcozy.cozy.signin.LoginActivity

class SplashActivity : AppCompatActivity() {

    val SPLASH_VIEW_TIME: Long = 1000 //2초간 스플래시 화면을 보여줌 (ms)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val isFirst = sharedPreferences.getBoolean("isFirst",true)

        Handler().postDelayed({ //delay를 위한 handler
            if(isFirst) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
                Log.d("온보딩뷰로 가기",isFirst.toString())
            }
            else{
                startActivity(Intent(this, LoginActivity::class.java))
                Log.d("로그인뷰로 가기",isFirst.toString())
                finish()
            }
        }, SPLASH_VIEW_TIME)
    }
}