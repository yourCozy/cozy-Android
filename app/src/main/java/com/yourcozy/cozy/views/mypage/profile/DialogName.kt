package com.yourcozy.cozy.views.mypage.profile

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.requestData.RequestNick

class DialogName(context: Context) : Dialog(context) {
    private val dlg = Dialog(context)
    private lateinit var btnConfirm: Button
    private lateinit var editNickname: EditText
    private lateinit var btnClose: ImageView
    val service = RequestToServer.service
    lateinit var sharedPref: SharedPreferences
    lateinit var changedNick : String
    lateinit var _listener : DialogInterface.OnDismissListener

    fun callDialog(context: Context) {
        Log.d("DIALOG_CHECK", "성공>>>>")
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.custom_dialog_name_change)
        dlg.setCancelable(false)
        dlg?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        sharedPref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)


        btnConfirm = dlg.findViewById(R.id.btn_change_name)
        btnClose = dlg.findViewById(R.id.iv_close)
        editNickname = dlg.findViewById(R.id.et_change_name)


        editNickname.addTextChangedListener(textWatcher)


        btnConfirm.setOnClickListener {
            val header = mutableMapOf<String, String?>()
            header["Content-Type"] = "application/json"
            header["token"] = sharedPref.getString("token", "token")
            changedNick = editNickname.text.toString()
            service.requestNickChanged(
                RequestNick(nickname = changedNick), header).customEnqueue(
                onError = {
                    Log.d("NAME CHANGE", "닉네임 불러오기>>>${changedNick}")
                },
                onSuccess = {
                    if (it.body()!!.success) {
                        Log.d("DIALOG_CHECK", "닉네임 변경 성공${changedNick}")
                    }
                }
            )
            dlg.dismiss()//커스텀 다이얼로그 종료
            //이름 변경이 완료되었다고 띄워주기.
            val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.custom_toast_nick_changed, null)

            with(Toast(context)){
                setGravity(Gravity.CENTER, 0, 0)
                duration = Toast.LENGTH_SHORT
                view = layout
                show()
            }


        }

        btnClose.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            btnConfirm.isEnabled = p0?.length != 0
        }
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        _listener = listener!!
    }
}