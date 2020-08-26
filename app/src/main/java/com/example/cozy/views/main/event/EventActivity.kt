package com.example.cozy.views.main.event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.R
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity(){

    lateinit var eventAdapter : EventAdapter
    var data = mutableListOf<EventData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)


        eventAdapter = EventAdapter(view.context)
        rc_event.adapter = eventAdapter
        loadData()
        rc_event.addItemDecoration(EventLeftItemDecoration(this, 8))

    }



    fun loadData() {
        data.apply {
            add(
                EventData(
                    event_image = "https://cdn.pixabay.com/photo/2018/03/26/02/05/cat-3261420__340.jpg"
                )
            )
            add(
                EventData(
                    event_image = "https://cdn.pixabay.com/photo/2018/03/26/02/05/cat-3261420__340.jpg"
                )
            )
            add(
                EventData(
                    event_image = "https://cdn.pixabay.com/photo/2018/03/26/02/05/cat-3261420__340.jpg"
                )
            )
            add(
                EventData(
                    event_image = "https://cdn.pixabay.com/photo/2018/03/26/02/05/cat-3261420__340.jpg"
                )
            )
            add(
                EventData(
                    event_image = "https://cdn.pixabay.com/photo/2018/03/26/02/05/cat-3261420__340.jpg"
                )
            )
        }

        eventAdapter.data = data
        eventAdapter.notifyDataSetChanged()
    }
}