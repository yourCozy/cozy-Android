package com.yourcozy.cozy.views.mypage.profile

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.requestData.RequestEmailPC
import com.yourcozy.cozy.network.requestData.RequestNewPwd
import java.util.regex.Pattern
import kotlin.properties.Delegates

class DialogChangePwd(context: Context) {
    private val dlg = Dialog(context)

    private lateinit var btnConfirm: Button
    private lateinit var btnClose: ImageView
//    private lateinit var titleText: TextView
    private lateinit var etInputFirst: EditText //이메일번호 입력창
    private lateinit var etInputSecond: EditText //인증번호 입력창
    private lateinit var btnRequestAgain: Button //인증번호 재요청
    private lateinit var textCountDown: TextView //180초 카운트다운
    private lateinit var warningMsg: TextView
    private lateinit var view : View
    private lateinit var regexMsg: TextView


    var isEmail = false
    var isEmailValid = false
    var emailChecked = false
    var isPwdValid = false

    private var authCode by Delegates.notNull<Int>()

    private lateinit var countDownTimer: CountDownTimer
    final var MILLISINFUTURE : Long= 180 * 1000
    final var countdownINTERVAL : Long = 1000

    private var count = 180

    val service = RequestToServer.service
    lateinit var sharedPref: SharedPreferences

    fun callDialog(context: Context) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.custom_dialog_pwd_first)
        dlg.setCancelable(false)
        dlg?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        sharedPref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)

        view = dlg.findViewById(R.id.view_pwd_change)
        btnConfirm = dlg.findViewById(R.id.btn_change_pwd)
        btnClose = dlg.findViewById(R.id.iv_pwd_change_close)
        etInputFirst = dlg.findViewById(R.id.et_change_pwd_email)
        etInputSecond = dlg.findViewById(R.id.et_pwd_email_auth)
        btnRequestAgain = dlg.findViewById(R.id.btn_request_auth)
        textCountDown = dlg.findViewById(R.id.tv_countdown)
        warningMsg = dlg.findViewById(R.id.tv_pwd_warn)
//        titleText = dlg.findViewById(R.id.tv_pwd_change_title)

        val header = mutableMapOf<String, String?>()
        header["Content-Type"] = "application/json"
        header["token"] = sharedPref.getString("token", "token")

        etInputFirst.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val emailAddress = etInputFirst.text.toString()
                if(s!!.isNullOrBlank()){
                    isEmail = false
                    btnConfirm.isEnabled = false
                }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
                    isEmail = false
                    warningMsg.text = "이메일 형식을 확인해주세요."
                    warningMsg.visibility = View.VISIBLE //이메일 형식이 아니면 이거 띄우기.
                    btnConfirm.isEnabled = false
                }else{
                    isEmail = true
                    warningMsg.visibility = View.GONE
                    btnConfirm.isEnabled = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        etInputSecond.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                btnConfirm.isEnabled = p0?.length != 0
            }

            override fun afterTextChanged(p0: Editable?) {
//                btnConfirm.isEnabled = p0?.length != 0
            }
        })

        btnConfirm.setOnClickListener {
            val emailAddress = etInputFirst.text.toString()
            Log.d("DIALOG_CHECK","이메일 주소 받아오기>>>${emailAddress}")

            if (isEmail && emailChecked) {
                /* 이메일 형식이 맞으면 통신 붙여서 onScuccess 인 경우엔 etInputAuth 활성화 시키고 etInputEmail 비활성화 시키기.
                    그리고 btnConfirm은 다시 enable로 만들기.
                    onFail에서"message": "회원가입시 등록한 이메일을 입력해주세요." 이거랑 일치하면 warningText 다시 셋팅해서 보여주기.
                */
                Log.d("DIALOG_CHECK", "이메일 양식이랑 맞다!")
                val num : Int = Integer.parseInt(etInputSecond.text.toString())
                if (authCode == num) {
                    warningMsg.visibility = View.GONE

//                    dlg.show()
                    dlg.dismiss()
                    dlg.setContentView(R.layout.custom_dialog_pwd_second)
                    callPwdSetting(context)
                }else if(count == 0) {
                    warningMsg.text = "시간이 초과되었어요."
                }else {//인증번호 다름.
                        warningMsg.text = "인증번호가 다릅니다."
                        warningMsg.visibility = View.VISIBLE
                }
            }else{// 이게 작동할 때는 최초 클릭 only
                service.requestPwdChangeEmail(RequestEmailPC(emailAddress), header).customEnqueue(
                    onError = {},
                    onSuccess = {
                        if (it.body()!!.message == "회원가입시 등록한 이메일을 입력해주세요."){
                            warningMsg.text = "회원 가입 시 등록한 이메일을 입력해 주세요."
                            warningMsg.visibility = View.VISIBLE
                        }
                        if (it.body()!!.success && it.body()!!.message == "이메일 발송 성공") {
                            //재요청 버튼 활성화, etInutAuth활성화
                            emailChecked = true
                            etInputFirst.isEnabled = false
                            btnRequestAgain.isEnabled = true
                            etInputSecond.isEnabled = true
                            textCountDown.isEnabled = true

                            btnConfirm.text = "확인"
                            authCode = it.body()!!.data.authCode
                            Log.d("DIALOG_CHECK", "인증번호>>>>${authCode}")

                            etInputSecond.visibility = View.VISIBLE
                            btnRequestAgain.visibility = View.VISIBLE
                            textCountDown.visibility = View.VISIBLE
                            countDownTimer()

                        }
                    }
                )
            }
        }
        btnClose.setOnClickListener {
            dlg.dismiss()
        }

        btnRequestAgain.setOnClickListener {
            val emailAddress = etInputFirst.text.toString()
            service.requestPwdChangeEmail(RequestEmailPC(emailAddress),headers = header).customEnqueue(
                onError = {},
                onSuccess = {
                    if(it.body()!!.success){
                        authCode = it.body()!!.data.authCode
                        Log.d("DIALOG_CHECK", "인증번호>>>>${authCode}")
                        try{
                            countDownTimer.cancel()
                        }catch (e: Exception) {}
                        count = 180
                        countDownTimer()
                        warningMsg.visibility = View.GONE
                    }
                }
            )
        }

        dlg.show()

//            if(etInputEmail.length() != null && etInputAuth.length() != null){
//                dlg.dismiss()
//            }
    }

    fun callPwdSetting(context: Context){
//        dlg.setContentView(R.layout.custom_dialog_pwd_second)
//        dlg?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        sharedPref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)

        view = dlg.findViewById(R.id.view_pwd_second)
        btnConfirm = dlg.findViewById(R.id.btn_change_pwd_second)
        btnClose = dlg.findViewById(R.id.iv_pwd_second_close)
        etInputFirst = dlg.findViewById(R.id.et_new_pwd)
        etInputSecond = dlg.findViewById(R.id.et_new_pwd_check)
        regexMsg = dlg.findViewById(R.id.tv_pwd_regex_check)
        warningMsg = dlg.findViewById(R.id.tv_pwd_not_match)
//        titleText = dlg.findViewById(R.id.tv_)
        val header = mutableMapOf<String, String?>()
        header["Content-Type"] = "application/json"
        header["token"] = sharedPref.getString("token", "token")

        etInputFirst.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val pwd = etInputFirst.text.toString()
                if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{10,20}$", pwd)){
                    isPwdValid = false
                    regexMsg.setTextColor(context.resources.getColor(R.color.notice))
                    btnConfirm.isEnabled = false
                }else{
                    regexMsg.setTextColor(context.resources.getColor(R.color.sub_color))
                    isPwdValid = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        etInputSecond.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if ((etInputFirst.length() != 0 && isPwdValid) && etInputFirst.text.toString() == etInputSecond.text.toString()) {
                    btnConfirm.isEnabled = true
                    warningMsg.visibility = View.GONE
                }else{
                    btnConfirm.isEnabled = false
                    warningMsg.visibility = View.VISIBLE
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        btnClose.setOnClickListener {
            dlg.dismiss()
        }

        btnConfirm.setOnClickListener {
            val newPwd: String = etInputFirst.text.toString()

            service.requestPwdChange(RequestNewPwd(newPwd), header).customEnqueue(
                onError = {},
                onSuccess = {
                    if(it.body()!!.success){
                        Log.d("DIALOG_CHECK", ">>>비밀번호 변경 완료")
                        //커스텀 토스트 띄우기.
                    }
                }
            )
            dlg.dismiss()

            val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.custom_toast_pwd_changed, null)

            with(Toast(context)) {
                setGravity(Gravity.CENTER, 0, 0)
                duration = Toast.LENGTH_SHORT
                view = layout
                show()
            }
        }

        dlg.show()


    }

    fun countDownTimer(){
        countDownTimer = myTimer(MILLISINFUTURE, countdownINTERVAL)
        countDownTimer.start()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            btnConfirm.isEnabled = p0?.length != 0
        }

        override fun afterTextChanged(p0: Editable?) {
            btnConfirm.isEnabled = p0?.length != 0
        }
    }

    inner class myTimer(millis : Long, interval : Long) : CountDownTimer(millis, interval) {
        override fun onTick(p0: Long) {
            textCountDown.text = count.toString() + "초"
            count--
        }

        override fun onFinish() {
            textCountDown.text = "0초"
        }

    }

}

