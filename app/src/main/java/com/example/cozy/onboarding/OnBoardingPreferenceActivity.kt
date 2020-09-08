package com.example.cozy.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.cozy.LoginActivity
import com.example.cozy.MainActivity
import com.example.cozy.R
import kotlinx.android.synthetic.main.activity_on_boarding_preference.*

class OnBoardingPreferenceActivity : AppCompatActivity() {

    var genres = mutableSetOf("")
    var activities = arrayOfNulls<String>(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_preference)

        genres.clear()
        btn_go_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        genre_art.setOnClickListener{
            if(genre_art.isChecked){
                genres.add(genre_art.text.toString())
            }
        }
    }
}