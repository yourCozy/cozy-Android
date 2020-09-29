package com.yourcozy.cozy.signin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yourcozy.cozy.MainActivity
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.requestData.RequestEmailLogin
import kotlinx.android.synthetic.main.activity_email_signin.*

class EmailLoginActivity : AppCompatActivity(){

    var isemail  = false
    var ispassword = false
    val requestToServer = RequestToServer
    lateinit var sharedPref : SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_signin)

        setSupportActionBar(tb_email_login)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_x)

        tb_email_login.elevation = 5F

        tv_forget_pw.text = Html.fromHtml("<u>아이디/비밀번호를 잊어버리셨나요?</u>")


        et_email_login.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(s!!.isNullOrBlank()){
                        isemail = false
                        login_finish()
                    }
                    else{
                        isemail = true
                        login_finish()
                    }
            }
        })

        et_pw_login.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s!!.isNullOrBlank()){
                    ispassword = false
                    login_finish()
                }
                else{
                    ispassword = true
                    login_finish()
                }
            }
        })

        btn_finish_login.setOnClickListener{
            requestToServer.service.requestEmailLogin(
                RequestEmailLogin(
                    email = et_email_login.text.toString(),
                    password = et_pw_login.text.toString()
                )
            ).customEnqueue(
                onError = { Log.d("이메일 통신 에러", "onError!!!!")},
                onSuccess = {
                    val data = it.body()!!.data
                    if(it.body()!!.message == "존재하지 않는 회원입니다.")
                    {
                        check_email.visibility = View.VISIBLE
                        check_pw.visibility = View.GONE
                    }
                    if(it.body()!!.message == "비밀번호를 확인해주세요.")
                    {
                        check_email.visibility = View.GONE
                        check_pw.visibility = View.VISIBLE
                    }
                    if(it.body()!!.success){
                        editor.putString("token", data.accessToken)
                        editor.putString("nickname", data.nickname)
                        editor.putString("email", data.email)
                        editor.apply()
                        editor.commit()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            )

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

    private fun login_finish(){
        if(isemail && ispassword) {
            btn_finish_login.isEnabled
            btn_finish_login.isSelected = true
        }
        else{
            btn_finish_login.isSelected = false
        }

    }



}


