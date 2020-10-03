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
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.requestData.RequestEmailPC
import retrofit2.HttpException

class DialogChangePwd(context: Context) {
    private val dlg = Dialog(context)
    private lateinit var btnConfirm: Button
    private lateinit var btnClose: ImageView
    private lateinit var etInputEmail: EditText //이메일번호 입력창
    private lateinit var etInputAuth: EditText //인증번호 입력창
    private lateinit var btnRequestAgain: Button //인증번호 재요청
    private lateinit var textCountDown: TextView //180초 카운트다운
    private lateinit var warningMsg: TextView

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

        btnConfirm = dlg.findViewById(R.id.btn_change_pwd)
        btnClose = dlg.findViewById(R.id.iv_pwd_change_close)
        etInputEmail = dlg.findViewById(R.id.et_change_pwd_email)
        etInputAuth = dlg.findViewById(R.id.et_pwd_email_auth)
        btnRequestAgain = dlg.findViewById(R.id.btn_request_auth)
        textCountDown = dlg.findViewById(R.id.tv_countdown)
        warningMsg = dlg.findViewById(R.id.tv_pwd_warn)

        etInputEmail.addTextChangedListener(textWatcher)
        etInputAuth.addTextChangedListener(textWatcher)

        btnConfirm.setOnClickListener {
            val header = mutableMapOf<String, String?>()
            header["Content-Type"] = "application/json"
            header["token"] = sharedPref.getString("token", "token")
            val emailAddress = etInputEmail.text.toString()
            Log.d("DIALOG_CHECK","이메일 주소 받아오기>>>${emailAddress}")

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) { //이메일 형식 체크
                warningMsg.text = "이메일 형식을 확인해주세요."
                warningMsg.visibility = View.VISIBLE//이메일 형식이 아니면 이거 띄우기.

            }/* else */
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                /* 이메일 형식이 맞으면 통신 붙여서 onScuccess 인 경우엔 etInputAuth 활성화 시키고 etInputEmail 비활성화 시키기.
                    그리고 btnConfirm은 다시 enable로 만들기.
                    onFail에서"message": "회원가입시 등록한 이메일을 입력해주세요." 이거랑 일치하면 warningText 다시 셋팅해서 보여주기.
                */
                Log.d("DIALOG_CHECK", "이메일 양식이랑 맞다!")
                service.requestPwdChangeEmail(RequestEmailPC(emailAddress), header)
                    .customEnqueue(
                       /* onFail = {
                            Log.d("DIALOG_CHECK", ">>통신 작동 중")
                            warningMsg.text = "회원 가입 시 등록한 이메일을 입력해 주세요."
                            warningMsg.visibility = View.VISIBLE//이메일 형식이 아니면 이거 띄우기.
                        },*/
                        onError = {
//                            if (it.equals("회원가입시 등록한 이메일을 입력해주세요.")) {
                                warningMsg.text = "회원 가입 시 등록한 이메일을 입력해 주세요."
                                warningMsg.visibility = View.VISIBLE//이메일 형식이 아니면 이거 띄우기.
//                            }
                            val httpException = it as HttpException
                            val errorBody = httpException.response()?.errorBody()!!
                            Log.d("DIALOG_CHECK", "통신 실패 이유>>>>${errorBody.toString()}")
//                        if(errorBody"회원가입시 등록한 이메일을 입력해주세요."))
                        },
                        onSuccess = {
                            if (it.body()!!.success) {

                            } else {


                            }
                        }
                    )
            }
        }
        btnClose.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()

//            if(etInputEmail.length() != null && etInputAuth.length() != null){
//                dlg.dismiss()
//            }
    }

    fun countDownTimer(){
        countDownTimer = myTimer(MILLISINFUTURE, countdownINTERVAL)
        countDownTimer.start()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

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

