package com.yourcozy.cozy

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.requestData.RequestFindPW
import com.yourcozy.cozy.signin.FindPWActivity


class DialogFindPW  (context: Context){
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK : Button
    private lateinit var email : EditText
    private lateinit var btnClose : ImageView
    private lateinit var listener : MyDialogOKClickedListener
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    fun start(context: Context) {

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_pw_find)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //색 투명하게

        pref = context.getSharedPreferences("EMAIL", Context.MODE_PRIVATE)
        editor = pref.edit()

        //dlg?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        email = dlg.findViewById(R.id.et_find_pwd_email)
        btnOK = dlg.findViewById(R.id.btn_find_pwd)
        btnClose = dlg.findViewById(R.id.iv_pwd_find_close)
        email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s!!.isNullOrBlank()){
                    btnOK.isEnabled = false
                    btnOK.isSelected = false
                }
                else{
                    btnOK.isEnabled = true
                    btnOK.isSelected = true
                }
            }
        })


        btnOK.setOnClickListener {
            listener.onOKClicked(false)
            val email_text = email.text.toString()
            editor.putString("email", email_text)
            editor.apply()
            editor.commit()
            Log.d("인증 요청", email_text)
            dlg.dismiss()
        }


        dlg.show()
    }




    fun setOnOKClickedListener(listener: (Boolean) -> Unit) {
        this.listener = object: MyDialogOKClickedListener {
            override fun onOKClicked(content: Boolean) {
                listener(content)
            }
        }
    }


    interface MyDialogOKClickedListener {
        fun onOKClicked(content : Boolean)
    }
}

