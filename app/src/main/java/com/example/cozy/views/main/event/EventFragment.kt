package com.example.cozy.views.main.event

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.views.main.RecommendDetailActivity
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventFragment : Fragment() {

    lateinit var eventAdapter: EventAdapter
    val datas = mutableListOf<EventData>()
    val service = RequestToServer.service
    val eventData = mutableListOf<EventData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_event, container, false)

        initActivity(view)

        return view
    }

    private fun initActivity(view: View) {
        eventAdapter = EventAdapter(view.context) { EventData, View ->
                var intent = Intent(activity as RecommendDetailActivity, EventDetailActivity::class.java)
                startActivity(intent)
            }
        view.rv_activity.adapter = eventAdapter

        loadData(view)
    }

    private fun loadData(view: View) {
        service.requestBookstoreActivity(0).customEnqueue(
            onError = {},
            onSuccess = {
                if(it.success) {
                    eventData.clear()
                    eventData.addAll(it.data)
                    eventAdapter.datas = eventData
                    view.rv_activity.addItemDecoration(ItemDecoration(this.context!!, 0,40))
                    eventAdapter.notifyDataSetChanged()
                }
            }
        )
    }

}
