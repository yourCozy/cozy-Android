package com.example.cozy.views.mypage.Interest

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cozy.DialogBookmark
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue

class InterestsAdapter(val context: Context, var data : MutableList<InterestsData>, val onEmpty : () -> Unit, val itemClick: (InterestsData, View) -> Unit) :
    RecyclerView.Adapter<InterestsViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestsViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_interest, parent, false)
        return InterestsViewHolder(
            view,
            itemClick
        )
    }

    override fun onBindViewHolder(holder: InterestsViewHolder, position: Int) {
        holder.bind(data[position])

        holder.bookmark.setImageResource(R.drawable.icon_save_full)

        //북마크 해제 온클릭리스터 추가
        holder.bookmark.setOnClickListener{
            val sharedPref = context.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "token")
            val header = mutableMapOf<String, String?>()
            header["Content-Type"] = "application/json"
            header["token"] = token

            val customDialog = DialogBookmark(context!!)
            customDialog.start()
            customDialog.setOnOKClickedListener {
                RequestToServer.service.requestBookmarkUpdate(data[position].bookstoreIdx, header)
                    .customEnqueue(
                        onError = { Log.d("RESPONSE", "error") },
                        onSuccess = {
                            if (it.success) {
                                Log.d("RESPONSE", it.message)
                                data.removeAt(position)
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position, data.size)
                                if(data.size == 0) onEmpty()
                            }
                            else Log.d("RESPONSE", it.message)
                        }
                    )
            }



        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(item: InterestsData) {
        data.add(item)
    }
}