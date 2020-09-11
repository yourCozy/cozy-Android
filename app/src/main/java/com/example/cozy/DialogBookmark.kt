package com.example.cozy


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button



class DialogBookmark(context : Context){
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var listener : MyDialogOKClickedListener

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.bookmark_cancel_custom_toast)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //색 투명하게
        //dlg?.window?.requestFeature(Window.FEATURE_NO_TITLE)


        btnOK = dlg.findViewById(R.id.btn_yes)
        btnOK.setOnClickListener {
            listener.onOKClicked(false)
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.btn_no)
        btnCancel.setOnClickListener {
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