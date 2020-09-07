package com.example.cozy.views.main.event

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlin.properties.Delegates

class EventDetailActivity : AppCompatActivity(){

    val service = RequestToServer.service
    lateinit var eventdetailAdapter : EventDetailAdapter
    var data = mutableListOf<EventDetailData>()
    var activityIdx by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        setSupportActionBar(event_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        event_toolbar.elevation = 5F

//        eventdetailAdapter = EventDetailAdapter(view.context)
        rc_event.adapter = eventdetailAdapter
        loadData()
    }



    fun loadData() {
        val sharedPref = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val header = mutableMapOf<String, String?>()
        header["Context-Type"] = "application/json"
        header["token"] = sharedPref.getString("token", "token")
        service.requestEventDetail(activityIdx, header).customEnqueue(
            onError = {

            },
            onSuccess = {
                if(it.success){
                    eventdetailAdapter = EventDetailAdapter(this, it.data.toMutableList())
                }
            }
        )
//        data.apply {
//            add(
//                EventDetailData(
//                    img = "https://cdn.pixabay.com/photo/2018/03/26/02/05/cat-3261420__340.jpg"
//                )
//            )
//            add(
//                EventDetailData(
//                    img = "https://cdn.pixabay.com/photo/2018/03/26/02/05/cat-3261420__340.jpg"
//                )
//            )
//            add(
//                EventDetailData(
//                    img = "https://cdn.pixabay.com/photo/2018/03/26/02/05/cat-3261420__340.jpg"
//                )
//            )
//            add(
//                EventDetailData(
//                    img = "https://cdn.pixabay.com/photo/2018/03/26/02/05/cat-3261420__340.jpg"
//                )
//            )
//            add(
//                EventDetailData(
//                    img = "https://cdn.pixabay.com/photo/2018/03/26/02/05/cat-3261420__340.jpg"
//                )
//            )
//        }
//
//        eventdetailAdapter.data = data
        rc_event.addItemDecoration(ItemDecoration(this, 8,0))
        eventdetailAdapter.notifyDataSetChanged()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.search,menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.search -> Toast.makeText(this,"검색", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}