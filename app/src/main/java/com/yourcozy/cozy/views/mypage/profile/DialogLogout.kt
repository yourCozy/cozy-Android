package com.yourcozy.cozy.views.mypage.profile

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.yourcozy.cozy.DialogBookmark
import com.yourcozy.cozy.R

class DialogLogout(context: Context){
    private val dlg = Dialog(context)
    private lateinit var btnYes: Button
    private lateinit var btnCancel: Button
    private lateinit var listener : DialogOKClickedListener

    fun callDialog() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.custom_dialog_logout)
        dlg.setCancelable(false)
        dlg?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnYes = dlg.findViewById(R.id.btn_logout_yes)
        btnCancel = dlg.findViewById(R.id.btn_logout_cancel)

        btnYes.setOnClickListener {
            listener.onOKClicked(false)
            dlg.dismiss()
        }
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnOKClickedListener(listener: (Boolean) -> Unit) {
        this.listener = object: DialogOKClickedListener {
            override fun onOKClicked(content: Boolean) {
                listener(content)
            }
        }
    }


    interface DialogOKClickedListener {
        fun onOKClicked(content : Boolean)
    }

}