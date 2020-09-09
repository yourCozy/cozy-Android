package com.example.cozy.views.main.event

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.views.main.RecommendDetailActivity
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlin.properties.Delegates

class EventFragment : Fragment() {
    val service = RequestToServer.service
    lateinit var eventAdapter: EventAdapter
    var data = mutableListOf<EventData>()
    var bookstoreIdx by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_event, container, false)

        val bundle = arguments

        bookstoreIdx = bundle!!.getInt("bookstoreIdx")


        initActivity()

        return view
    }

    private fun initActivity() {
        val sharedPref = activity!!.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val header = mutableMapOf<String, String?>()
        header["Context-Type"] = "application/json"
        header["token"] = sharedPref.getString("token","token")
        service.requestEvent(bookstoreIdx,header).customEnqueue(
            onError = { Log.d("event_from_each : ", "가져오기 실패") },
            onSuccess = {
                if (it.success) {
                    if (it.message == "활동 리스트 조회 성공") {
                        eventAdapter = EventAdapter(context!!,it.data.toMutableList()) { EventData, View ->
                            var intent = Intent(
                                activity as RecommendDetailActivity,
                                EventDetailActivity::class.java
                            )
                            intent.putExtra("activityIdx", EventData.activityIdx)
                            startActivity(intent)
                        }
                        rv_activity.adapter = eventAdapter

                    }
                }else Log.d("event_from_each : ","활동 리스트가 없어요.")
            }
        )
    }

    private fun loadData() {
        data.apply{
            for (i in 0..7) {
                add(
                    EventData(
                        activityIdx = 1,
                        activityName = "책방 영화관",
                        shortIntro = "홍철책방을 대표하는 책방 영화관이 돌아왔습니다:-)",
                        image1 = "gg",
                        dday = 3,
                        price = 16000
                    )
                )
            }
        }
        eventAdapter.data = data
        rv_activity.addItemDecoration(ItemDecoration(this.context!!, 0,40))
        eventAdapter.notifyDataSetChanged()
    }

}
