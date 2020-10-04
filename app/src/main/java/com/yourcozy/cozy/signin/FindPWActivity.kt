package com.yourcozy.cozy.signin

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yourcozy.cozy.R
import kotlinx.android.synthetic.main.activity_check_userinfo.*

class FindPWActivity() : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_userinfo)

        setSupportActionBar(tb_check_user)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_x)

        tb_check_user.elevation = 5F

        if(intent.getStringExtra("userEmail") == "no"){
            tv_not_user.visibility = View.VISIBLE
            btn_go_signup.visibility = View.VISIBLE
            rounded_iv_userprofile.visibility = View.GONE
            textView10.visibility = View.GONE
            email.visibility = View.GONE
            btn_check_userinfo.visibility = View.GONE
        }
        else{
            tv_not_user.visibility = View.GONE
            btn_go_signup.visibility = View.GONE
            rounded_iv_userprofile.visibility = View.VISIBLE
            textView10.visibility = View.VISIBLE
            email.visibility = View.VISIBLE
            btn_check_userinfo.visibility = View.VISIBLE
            email.text = intent.getStringExtra("userEmail")
        }

        btn_go_signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_check_userinfo.setOnClickListener {
            Toast.makeText(this, "해당 메일로 임시 비밀번호를 보냈습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}