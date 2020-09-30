package com.yourcozy.cozy.signin

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.yourcozy.cozy.KeyBoardControl
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.requestData.RequestCheckEmail
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity(){

    private lateinit var keyBoardControl : KeyBoardControl
    val requestToServer = RequestToServer
    lateinit var imm : InputMethodManager
    var isnickname = false
    var isemail = false
    var password = false
    var passwordcheck = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setSupportActionBar(tb_signup)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_x)

        tb_signup.elevation = 5F

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager


        keyBoardControl = KeyBoardControl(window,
            onShowKeyboard = { keyboardHeight ->
                scroll_signup.run {
                    smoothScrollTo(scrollX, scrollY + keyboardHeight/4)
                }
            })


        et_signup_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s!!.isNullOrBlank()){
                    tv_name_check.visibility = View.VISIBLE
                    isnickname = false
                    signup_finish()
                }
                else if(!Pattern.matches("[a-zA-Z0-9ㄱ-ㅎ가-힣].{0,9}+\$", et_signup_name.text.toString())){
                    tv_name_check.visibility = View.VISIBLE
                }
                else{
                    tv_name_check.visibility = View.GONE
                    isnickname = true
                    signup_finish()
                }
            }
        })

        et_signup_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s!!.isNullOrBlank()){
                    isemail = false
                    iv_email_check.visibility = View.GONE
                    tv_email_check.visibility = View.GONE
                    signup_finish()
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(et_signup_email.text.toString()).matches()){
                    isemail = false
                    iv_email_check.visibility = View.GONE
                    tv_email_check.visibility = View.VISIBLE
                    tv_email_check.text = "이메일 형식을 확인해주세요."
                    tv_email_check.setTextColor(resources.getColor(R.color.sub_color))
                    signup_finish()
                }
                else{
                    isemail = true
                    iv_email_check.visibility = View.GONE
                    tv_email_check.visibility = View.GONE
                    signup_finish()
                }
            }
        })

        check_overlap_email.setOnClickListener{
            requestToServer.service.requestCheckEmail(
                RequestCheckEmail(
                    email = et_signup_email.text.toString()
                )
            ).customEnqueue(
                onError = {},
                onSuccess = {
                    if(it.body()!!.message == "이미 등록된 이메일입니다."){
                        iv_email_check.visibility = View.GONE
                        tv_email_check.visibility = View.VISIBLE
                        tv_email_check.text = "이미 사용 중인 이메일입니다."
                        tv_email_check.setTextColor(resources.getColor(R.color.sub_color))
                        isemail = false
                        signup_finish()
                    }
                    if(it.body()!!.success){
                        iv_email_check.visibility = View.VISIBLE
                        tv_email_check.visibility = View.VISIBLE
                        tv_email_check.text = "가능한 이메일입니다."
                        tv_email_check.setTextColor(resources.getColor(R.color.dark_gray))
                        isemail = true
                        signup_finish()
                    }
                }
            )
        }

        et_signup_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{10,20}$", et_signup_pw.text.toString())){
                    signup_pw_notice.setTextColor(resources.getColor(R.color.sub_color))
                    password = false
                    signup_finish()
                }
                else{
                    signup_pw_notice.setTextColor(resources.getColor(R.color.notice))
                    password = true
                    signup_finish()
                }
            }
        })

        et_signup_pw_check.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (et_signup_pw.text.toString() == et_signup_pw_check.text.toString()) {
                    notice_check_pw.visibility = View.GONE
                    passwordcheck = true
                    signup_finish()
                }
                else {
                    notice_check_pw.visibility = View.VISIBLE
                    notice_check_pw.text = "비밀번호가 일치하지 않아요."
                    passwordcheck = false
                    signup_finish()
                }
            }
        })

        btn_finish_signup.setOnClickListener {

        }

    }




    //키보드 내리기
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name
                .startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (this.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager).hideSoftInputFromWindow(
                this.window.decorView.applicationWindowToken, 0
            )
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun signup_finish(){
        if(isnickname&&isemail&&password&&passwordcheck) {
            btn_finish_signup.isEnabled
            btn_finish_signup.isSelected = true
        }
        else{
            btn_finish_signup.isSelected = false
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

    override fun onDestroy() {
        keyBoardControl.detachKeyboardListeners()
        super.onDestroy()
    }

}