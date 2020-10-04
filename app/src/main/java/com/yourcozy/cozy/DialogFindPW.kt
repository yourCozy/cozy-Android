package com.yourcozy.cozy

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.Button
import android.widget.EditText


class DialogFindPW  (context: Context){
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK : Button
    private lateinit var email : EditText
    private lateinit var listener : MyDialogOKClickedListener


    fun start() {

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_pw_find)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //색 투명하게

        //dlg?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        email = dlg.findViewById(R.id.et_email_userCheck)
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

        btnOK = dlg.findViewById(R.id.btn_check)
        btnOK.setOnClickListener {
            listener.onOKClicked(false)
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