package com.yourcozy.cozy.views

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.yourcozy.cozy.DialogBookmark
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.responseData.SearchData

class SearchAdapter(val context: Context,var data: MutableList<SearchData> , val itemClick : (SearchData, View) -> Unit) : RecyclerView.Adapter<SearchViewHolder>(){
    val service = RequestToServer.service
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_interest,parent,false)
        return SearchViewHolder(view,itemClick)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(data[position])

        holder.bookmark.setOnClickListener {
            val sharedPref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            val header = mutableMapOf<String, String>()
            header["Content-Type"] = "application/json"
            header["token"] = sharedPref.getString("token", "token").toString()
            if(header["token"] == "token"){
                Toast.makeText(context,"로그인 후 이용해 주세요.", Toast.LENGTH_SHORT).show()
            }else {
                if (!holder.bookmark.isSelected) {
                    service.requestBookmarkUpdate(holder.bookstoreIdx, header).customEnqueue(
                        onError = {
                            Toast.makeText(
                                context!!,
                                "올바르지 않은 요청입니다.",
                                Toast.LENGTH_SHORT
                            )
                        },
                        onSuccess = {
                            Log.d("Bookmark message", "${it.body()!!.message}")
                            Log.d("Bookmark checked11", "${it.body()!!.data}")
                            if (it.body()!!.success) {
                                val data = it.body()!!.data
                                Log.d("Bookmark checked22", "북마크 성공 ${data!!.checked}")
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
                                    Log.d("Bookmark message", "${it.body()!!.message}")
                                    Log.d("Bookmark checked11", "${it.body()!!.data}")

                                    if (it.body()!!.message != "북마크 체크/해제 성공") { //로그인 하지 않았을 때
                                        //팝업창 띄우기
                                    }
                                    if (it.body()!!.success) {
                                        Log.d("RESPONSE", it.body()!!.message)
                                        holder.bookmark.isSelected = false
                                        Log.d("Bookmark checked22", "북마크 해제${it.body()!!.data!!.checked}")
                                    }
                                }
                            )
                    }
                    customDialog.start()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}