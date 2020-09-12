package com.example.cozy.views.main.event

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.views.main.RecommendDetailActivity
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlin.properties.Delegates

class EventFragment : Fragment() {

    lateinit var eventAdapter: EventAdapter
    val service = RequestToServer.service
    val eventData = mutableListOf<EventData>()
    var bookstoreIdx by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_event, container, false)

        val extra = arguments
        bookstoreIdx = extra!!.getInt("bookstoreIdx")
        Log.d("feed >>>>> ",bookstoreIdx.toString())

        initActivity(view)

        return view
    }

    private fun initActivity(view: View) {
        eventAdapter = EventAdapter(view.context) { EventData, View ->
            if(EventData.dday < 0) {
                Toast.makeText(context,"마감된 활동입니다.",Toast.LENGTH_SHORT).show()
            }else {
                var intent = Intent(activity as RecommendDetailActivity, EventDetailActivity::class.java)
                intent.putExtra("activityIdx", EventData.activityIdx)
                startActivity(intent)
            }
        }
        view.rv_activity.adapter = eventAdapter

        loadData(view)
    }

    private fun loadData(view: View) {
        service.requestBookstoreActivity(bookstoreIdx).customEnqueue(
            onError = { Toast.makeText(context, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)},
            onSuccess = {
                if(it.success) {
                    Log.d("success message >>>> ",it.message)
                    Log.d("success data >>>> ",it.data.toString())
                    eventData.clear()
                    eventData.addAll(it.data)
                    eventAdapter.data = eventData
                    view.rv_activity.addItemDecoration(ItemDecoration(this.context!!, 0,40))
                    eventAdapter.notifyDataSetChanged()
                }else{
                    Log.d("non success message >>>> ",it.message)
                    view.tv_nonActivity.visibility = View.VISIBLE
                }
            }
        )
    }

    fun newInstance(bookstoreIdx: Int): EventFragment{
        val args = Bundle()
        val fragment = EventFragment()
        args.putInt("bookstoreIdx", bookstoreIdx)
        fragment.arguments = args
        return fragment
    }

}
