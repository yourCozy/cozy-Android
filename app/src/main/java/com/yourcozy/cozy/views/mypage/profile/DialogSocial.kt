package com.yourcozy.cozy.views.mypage.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.Button
import com.yourcozy.cozy.R

class DialogSocial(context: Context) {
    private val dlg = Dialog(context)
    private lateinit var btnConfirm : Button

    fun call(){
        Log.d("SOCIAL_SIGNIN_CHECK", "성공>>>>>>")
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.custom_dialog_social_alert)
        dlg.setCancelable(false)
        dlg?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnConfirm = dlg.findViewById(R.id.btn_social_alert_confirm)
        btnConfirm.setOnClickListener{
            dlg.dismiss()
        }

        dlg.show()
    }
}