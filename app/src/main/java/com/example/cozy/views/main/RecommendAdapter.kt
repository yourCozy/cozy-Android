package com.example.cozy.views.main

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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

        holder.save.setOnClickListener{
            val sharedPref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            val header = mutableMapOf<String, String>()
            header["Content-Type"] = "application/json"
//            header["token"] = sharedPref.getString("token","token").toString()
            header["token"] = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4Ijo4LCJpYXQiOjE1OTk1NDUwODMsImV4cCI6MTU5OTU4MTA4MywiaXNzIjoib3VyLXNvcHQifQ.5LiwFhnFJ-zLcuafwaGzHtjdlxIlM13sXgXdnb_G7q8"
            service.requestBookmarkUpdate(holder.bookstoreIdx,header).customEnqueue(
                onError = {Toast.makeText(context!!, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT) },
                onSuccess = {
                    Log.d("Bookmark message", "${it.message}")
                    Log.d("Bookmark checked11", "${it.data}")
                    if(it.success){
                        val data = it.data
                        if (data!!.checked == 1){
                            Log.d("Bookmark checked22", "북마크 성공 ${data!!.checked.toString()}")
                            holder.save.isSelected = true
                            val inflater : LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                            val layout = inflater.inflate(R.layout.bookmark_custom_toast,null)

                            with (Toast(context)) {
                                setGravity(Gravity.CENTER, 0, 0)
                                duration = Toast.LENGTH_SHORT
                                view = layout
                                show()
                            }
                        }
                        else{
                            holder.save.isSelected = false
                            Log.d("Bookmark checked22", "북마크 해제${data!!.checked.toString()}")
                        }
                    }
                }
            )
        }
    }

    fun addItem(item : RecommendData){
        datas.add(item)
    }
}