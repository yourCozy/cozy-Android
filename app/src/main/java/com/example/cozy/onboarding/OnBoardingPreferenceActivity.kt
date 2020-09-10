package com.example.cozy.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.cozy.LoginActivity
import com.example.cozy.MainActivity
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import kotlinx.android.synthetic.main.activity_on_boarding_preference.*

class OnBoardingPreferenceActivity : AppCompatActivity() {

    var genres = mutableSetOf<String>()
    var activities = mutableSetOf<String>()
    val service = RequestToServer.service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_preference)

        val sharedPref: SharedPreferences = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        pre_username.text = sharedPref.getString("nickname","나그네")

        genres.clear()
        activities.clear()
        btn_go_main.setOnClickListener {
            if(!(genres.size == 3 && activities.size == 3)){
                Toast.makeText(this,"각각 3개씩 총 6개를 선택해 주세요",Toast.LENGTH_SHORT).show()
            }
            else {
                btn_go_main.isEnabled = true
                val genreList = mutableListOf<String>()
                val activityList = mutableListOf<String>()
                genreList.addAll(genres)
                activityList.addAll(activities)
                Log.d("장르", genreList.toString())
                Log.d("활동", activityList.toString())
                val header = mutableMapOf<String, String>()
                header["Content-Type"] = "application/json"
                header["token"] = sharedPref.getString("token", "token").toString()
                service.requestPreference(header,genreList[0],genreList[1],genreList[2],activityList[0],activityList[1],activityList[2]).customEnqueue(
                    onError = {Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)},
                    onSuccess = {
                        if(it.success){
                            Log.d("취향선택 성공 >>> ",it.message)
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Log.d("취향선택 실패 >>> ",it.message)
                        }
                    }
                )
            }
        }

        genre_art.setOnClickListener {
            if(genre_art.isSelected){
                genre_art.isSelected = false
                removeGenre(genre_art.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_art.text.toString())
                    genre_art.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_vintage.setOnClickListener {
            if(genre_vintage.isSelected){
                genre_vintage.isSelected = false
                removeGenre(genre_vintage.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_vintage.text.toString())
                    genre_vintage.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_sensitivity.setOnClickListener {
            if(genre_sensitivity.isSelected){
                genre_sensitivity.isSelected = false
                removeGenre(genre_sensitivity.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_sensitivity.text.toString())
                    genre_sensitivity.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_independence.setOnClickListener {
            if(genre_independence.isSelected){
                genre_independence.isSelected = false
                removeGenre(genre_independence.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_independence.text.toString())
                    genre_independence.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_beer.setOnClickListener {
            if(genre_beer.isSelected){
                genre_beer.isSelected = false
                removeGenre(genre_beer.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_beer.text.toString())
                    genre_beer.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
            }
        genre_small.setOnClickListener {
            if(genre_small.isSelected){
                genre_small.isSelected = false
                removeGenre(genre_small.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_small.text.toString())
                    genre_small.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
            }
        genre_picture.setOnClickListener {
            if(genre_picture.isSelected){
                genre_picture.isSelected = false
                removeGenre(genre_picture.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_picture.text.toString())
                    genre_picture.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_coffee.setOnClickListener {
            if(genre_coffee.isSelected){
                genre_coffee.isSelected = false
                removeGenre(genre_coffee.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_coffee.text.toString())
                    genre_coffee.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_fairytale.setOnClickListener {
            if(genre_fairytale.isSelected){
                genre_fairytale.isSelected = false
                removeGenre(genre_fairytale.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_fairytale.text.toString())
                    genre_fairytale.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_curation.setOnClickListener {
            if(genre_curation.isSelected){
                genre_curation.isSelected = false
                removeGenre(genre_curation.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_curation.text.toString())
                    genre_curation.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
            }
        genre_magazine.setOnClickListener {
            if(genre_magazine.isSelected){
                genre_magazine.isSelected = false
                removeGenre(genre_magazine.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_magazine.text.toString())
                    genre_magazine.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_poem.setOnClickListener {
            if(genre_poem.isSelected){
                genre_poem.isSelected = false
                removeGenre(genre_poem.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_poem.text.toString())
                    genre_poem.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_science.setOnClickListener {
            if(genre_science.isSelected){
                genre_science.isSelected = false
                removeGenre(genre_science.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_science.text.toString())
                    genre_science.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_cozy.setOnClickListener {
            if(genre_cozy.isSelected){
                genre_cozy.isSelected = false
                removeGenre(genre_cozy.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_cozy.text.toString())
                    genre_cozy.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_humanity.setOnClickListener {
            if(genre_humanity.isSelected){
                genre_humanity.isSelected = false
                removeGenre(genre_humanity.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_humanity.text.toString())
                    genre_humanity.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_warm.setOnClickListener {
            if(genre_warm.isSelected){
                genre_warm.isSelected = false
                removeGenre(genre_warm.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_warm.text.toString())
                    genre_warm.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_stationery.setOnClickListener {
            if(genre_stationery.isSelected){
                genre_stationery.isSelected = false
                removeGenre(genre_stationery.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_stationery.text.toString())
                    genre_stationery.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        genre_cafe.setOnClickListener {
            if(genre_cafe.isSelected){
                genre_cafe.isSelected = false
                removeGenre(genre_cafe.text.toString())
            }
            else{
                if (genres.size < 3){
                    addGenre(genre_cafe.text.toString())
                    genre_cafe.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }

        activity_reading.setOnClickListener {
            if(activity_reading.isSelected){
                activity_reading.isSelected = false
                removeActivity(activity_reading.text.toString())
            }
            else{
                if (activities.size < 3){
                    addActivity(activity_reading.text.toString())
                    activity_reading.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        activity_workshop.setOnClickListener {
            if(activity_workshop.isSelected){
                activity_workshop.isSelected = false
                removeActivity(activity_workshop.text.toString())
            }
            else{
                if (activities.size < 3){
                    addActivity(activity_workshop.text.toString())
                    activity_workshop.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        activity_make.setOnClickListener {
            if(activity_make.isSelected){
                activity_make.isSelected = false
                removeActivity(activity_make.text.toString())
            }
            else{
                if (activities.size < 3){
                    addActivity(activity_make.text.toString())
                    activity_make.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        activity_movie.setOnClickListener {
            if(activity_movie.isSelected){
                activity_movie.isSelected = false
                removeActivity(activity_movie.text.toString())
            }
            else{
                if (activities.size < 3){
                    addActivity(activity_movie.text.toString())
                    activity_movie.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        activity_writing.setOnClickListener {
            if(activity_writing.isSelected){
                activity_writing.isSelected = false
                removeActivity(activity_writing.text.toString())
            }
            else{
                if (activities.size < 3){
                    addActivity(activity_writing.text.toString())
                    activity_writing.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        activity_lecture.setOnClickListener {
            if(activity_lecture.isSelected){
                activity_lecture.isSelected = false
                removeActivity(activity_lecture.text.toString())
            }
            else{
                if (activities.size < 3){
                    addActivity(activity_lecture.text.toString())
                    activity_lecture.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        activity_booktalk.setOnClickListener {
            if(activity_booktalk.isSelected){
                activity_booktalk.isSelected = false
                removeActivity(activity_booktalk.text.toString())
            }
            else{
                if (activities.size < 3){
                    addActivity(activity_booktalk.text.toString())
                    activity_booktalk.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        activity_midnight.setOnClickListener {
            if(activity_midnight.isSelected){
                activity_midnight.isSelected = false
                removeActivity(activity_midnight.text.toString())
            }
            else{
                if (activities.size < 3){
                    addActivity(activity_midnight.text.toString())
                    activity_midnight.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        activity_rental.setOnClickListener {
            if(activity_rental.isSelected){
                activity_rental.isSelected = false
                removeActivity(activity_rental.text.toString())
            }
            else{
                if (activities.size < 3){
                    addActivity(activity_rental.text.toString())
                    activity_rental.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        activity_exhibit.setOnClickListener {
            if(activity_exhibit.isSelected){
                activity_exhibit.isSelected = false
                removeActivity(activity_exhibit.text.toString())
            }
            else{
                if (activities.size < 3){
                    addActivity(activity_exhibit.text.toString())
                    activity_exhibit.isSelected = true
                }
                else{
                    Toast.makeText(this,"3개까지 선택 가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun removeGenre(genre: String){
        genres.remove(genre)
    }

    fun addGenre(genre: String){
        genres.add(genre)
    }

    fun removeActivity(genre: String){
        activities.remove(genre)
    }

    fun addActivity(genre: String){
        activities.add(genre)
    }
}