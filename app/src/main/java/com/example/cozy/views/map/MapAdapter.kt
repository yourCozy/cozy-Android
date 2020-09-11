package com.example.cozy.views.map

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.DialogBookmark
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue


class MapAdapter (private val context : Context, val data : MutableList<MapData>, val itemClick: (MapData, View) -> Unit) : RecyclerView.Adapter<MapViewHolder>(){

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bookstore_list,parent,false)
        return MapViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        holder.bind(data[position])

        holder.bookmark.setOnClickListener{
            val sharedPref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "token")
            val header = mutableMapOf<String, String?>()
            header["Content-Type"] = "application/json"
            header["token"] = token
            if(holder.bookmark.isSelected == false) {
                RequestToServer.service.requestBookmarkUpdate(data[position].bookstoreIdx, header).customEnqueue(
                        onError = { Log.d("RESPONSE", "error") },
                        onSuccess = {
                            if (it.message != "북마크 체크/해제 성공") { //로그인 하지 않았을 때
                                //팝업창 띄우기
                            }
                            if (it.success) {
                                Log.d("RESPONSE", it.message)
                                holder.bookmark.isSelected = true
                                val inflater: LayoutInflater =
                                    context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                                val layout = inflater.inflate(R.layout.bookmark_custom_toast, null)

                                with(Toast(context)) {
                                    setGravity(Gravity.CENTER, 0, 0)
                                    duration = Toast.LENGTH_SHORT
                                    view = layout
                                    show()
                                }
                            }
                        }
                    )
                }
            else {
                val customDialog = DialogBookmark(context!!)
                customDialog.start()
                customDialog.setOnOKClickedListener {
                    RequestToServer.service.requestBookmarkUpdate(data[position].bookstoreIdx, header)
                        .customEnqueue(
                            onError = { Log.d("RESPONSE", "error") },
                            onSuccess = {
                                if (it.message != "북마크 체크/해제 성공") { //로그인 하지 않았을 때
                                    //팝업창 띄우기
                                }
                                if (it.success) {
                                    Log.d("RESPONSE", it.message)
                                    holder.bookmark.isSelected = false
                                }
                            }
                        )
                }
            }

        }

    }
}

