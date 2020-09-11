package com.example.cozy.views.main

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.DialogBookmark
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue

class RecommendAdapter (private val context : Context, val itemClick: (RecommendData, View) -> Unit) : RecyclerView.Adapter<RecommendViewHolder>(){
    var datas = mutableListOf<RecommendData>()
    val service = RequestToServer.service
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recommend,parent,false)
        return RecommendViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.save.setOnClickListener {
            val sharedPref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            val header = mutableMapOf<String, String>()
            header["Content-Type"] = "application/json"
            header["token"] = sharedPref.getString("token", "token").toString()
            if(header["token"] == "token"){
                Toast.makeText(context,"로그인 후 이용해 주세요.",Toast.LENGTH_SHORT).show()
            }else {
                if (!holder.save.isSelected) {
                    service.requestBookmarkUpdate(holder.bookstoreIdx, header).customEnqueue(
                        onError = {
                            Toast.makeText(
                                context!!,
                                "올바르지 않은 요청입니다.",
                                Toast.LENGTH_SHORT
                            )
                        },
                        onSuccess = {
                            Log.d("Bookmark message", "${it.message}")
                            Log.d("Bookmark checked11", "${it.data}")
                            if (it.success) {
                                val data = it.data
                                Log.d("Bookmark checked22", "북마크 성공 ${data!!.checked}")
                                holder.save.isSelected = true
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
                        })
                } else {
                    val customDialog = DialogBookmark(context!!)
                    customDialog.setOnOKClickedListener {
                        RequestToServer.service.requestBookmarkUpdate(holder.bookstoreIdx, header)
                            .customEnqueue(
                                onError = {
                                    Toast.makeText(
                                        context!!,
                                        "올바르지 않은 요청입니다.",
                                        Toast.LENGTH_SHORT
                                    )
                                },
                                onSuccess = {
                                    Log.d("Bookmark message", "${it.message}")
                                    Log.d("Bookmark checked11", "${it.data}")

                                    if (it.message != "북마크 체크/해제 성공") { //로그인 하지 않았을 때
                                        //팝업창 띄우기
                                    }
                                    if (it.success) {
                                        Log.d("RESPONSE", it.message)
                                        holder.save.isSelected = false
                                        Log.d("Bookmark checked22", "북마크 해제${it.data!!.checked}")
                                    }
                                }
                            )
                    }
                    customDialog.start()
                }
            }
        }
    }

    fun addItem(item : RecommendData){
        datas.add(item)
    }
}