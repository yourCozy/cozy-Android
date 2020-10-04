package com.yourcozy.cozy.signin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.yourcozy.cozy.DialogFindPW
import com.yourcozy.cozy.MainActivity
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.requestData.RequestEmailLogin
import com.yourcozy.cozy.network.requestData.RequestFindPW
import com.yourcozy.cozy.onboarding.OnBoardingPreferenceActivity
import kotlinx.android.synthetic.main.activity_email_signin.*
import kotlinx.android.synthetic.main.dialog_pw_find.*

class EmailLoginActivity : AppCompatActivity(){

    var isemail  = false
    var ispassword = false
    val requestToServer = RequestToServer
    lateinit var imm : InputMethodManager
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var isLogined = 1

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

        sharedPref = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        editor = sharedPref.edit()

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        tv_forget_pw.text = Html.fromHtml("<u>비밀번호를 잊어버리셨나요?</u>")


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
            imm.hideSoftInputFromWindow(getCurrentFocus()!!.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
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
                        isLogined = data.is_logined
                        editor.putString("token", data.accessToken)
                        editor.putString("nickname", data.nickname)
                        editor.putString("email", data.email)
                        editor.putBoolean("isEmail",true)
                        editor.apply()
                        editor.commit()
                        val intent: Intent
                        if(isLogined == 0) {
                            intent = Intent(this, OnBoardingPreferenceActivity::class.java)
                            startActivity(intent)
                        }else{
                            intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        finish()
                    }
                }
            )

        }

        tv_forget_pw.setOnClickListener {
            val customDialog = DialogFindPW(this)
            customDialog.start()
            customDialog.setOnOKClickedListener{
                RequestToServer.service.requestFindPW(
                    RequestFindPW(
                        email = et_email_userCheck.text.toString()
                    )
                ).customEnqueue(
                    onError = {},
                    onSuccess = {
                        if(it.body()!!.message == "존재하지 않는 회원입니다.")
                        {
                            intent = Intent(this, FindPWActivity::class.java)
                            intent.putExtra("userEmail", 0)
                            startActivity(intent)
                        }
                        if(it.body()!!.success)
                        {
                            intent = Intent(this, FindPWActivity::class.java)
                            intent.putExtra("userEmail", it.body()!!.data.toEmail)
                            startActivity(intent)
                        }

                    }
                )
            }
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
            btn_finish_login.isEnabled = true
            btn_finish_login.isSelected = true
        }
        else{
            btn_finish_login.isEnabled = false
            btn_finish_login.isSelected = false
        }

    }



}


