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
        rc_event.addItemDecoration(EventBothSideItemDecoration(this, 20))
        rc_event.addItemDecoration(EventLeftItemDecoration(this, 8))
        loadData()

    }



    fun loadData() {
        data.apply {
            add(
                EventData(
                    event_image = "https://cdn.pixabay.com/photo/2014/09/05/18/32/old-books-436498_1280.jpg"
                )
            )
            add(
                EventData(
                    event_image = "https://cdn.pixabay.com/photo/2014/09/05/18/32/old-books-436498_1280.jpg"
                )
            )
            add(
                EventData(
                    event_image = "https://cdn.pixabay.com/photo/2014/09/05/18/32/old-books-436498_1280.jpg"
                )
            )
            add(
                EventData(
                    event_image = "https://cdn.pixabay.com/photo/2014/09/05/18/32/old-books-436498_1280.jpg"
                )
            )
            add(
                EventData(
                    event_image = "https://cdn.pixabay.com/photo/2014/09/05/18/32/old-books-436498_1280.jpg"
                )
            )
        }

        eventAdapter.data = data
        eventAdapter.notifyDataSetChanged()
    }
}