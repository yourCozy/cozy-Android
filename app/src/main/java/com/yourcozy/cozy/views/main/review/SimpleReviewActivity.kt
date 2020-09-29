package com.yourcozy.cozy.views.main.review

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.requestData.RequestSimpleReview
import kotlinx.android.synthetic.main.activity_simple_review.*
import kotlin.properties.Delegates

class SimpleReviewActivity : AppCompatActivity() {

    var facility : Int? = null
    var book: Int? = null
    var book_activity: Int? = null
    var dessert: Int? = null
    val service = RequestToServer.service
    var bookstoreIdx by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_review)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.elevation = 5F

        if (intent.hasExtra("bookstoreIdx")) {
            bookstoreIdx = intent.getIntExtra("bookstoreIdx",0)
        }

        rg_facility.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.facility_bad -> facility = 1
                R.id.facility_soso -> facility = 2
                R.id.facility_good -> facility = 3
            }
            if( facility != null && book != null && book_activity != null && dessert != null){
                btn_completion.isEnabled = true
            }
        }

        rg_book.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.book_bad -> book = 1
                R.id.book_soso -> book = 2
                R.id.book_good -> book = 3
            }
            if( facility != null && book != null && book_activity != null && dessert != null){
                btn_completion.isEnabled = true
            }
        }

        rg_activity.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.activity_bad -> book_activity = 1
                R.id.activity_soso -> book_activity = 2
                R.id.activity_good -> book_activity = 3
                R.id.activity_no -> book_activity = 4
            }
            if( facility != null && book != null && book_activity != null && dessert != null){
                btn_completion.isEnabled = true
            }
        }

        rg_dessert.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.dessert_bad -> dessert = 1
                R.id.dessert_soso -> dessert = 2
                R.id.dessert_good -> dessert = 3
                R.id.dessert_no -> dessert = 4
            }
            if( facility != null && book != null && book_activity != null && dessert != null){
                btn_completion.isEnabled = true
            }
        }

        btn_completion.setOnClickListener {
            val sharedPref = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
            val header = mutableMapOf<String, String>()
            header["Content-Type"] = "application/json"
            header["token"] = sharedPref.getString("token", "token").toString()
            service.requestSimpleReviewWrite(header,bookstoreIdx,
                RequestSimpleReview (
                    facilityNum = facility!!,
                    bookNum = book!!,
                    activityNum = book_activity!!,
                    foodNum = dessert!!)).customEnqueue(
                onError = {Toast.makeText(this, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)},
                onSuccess = {
                    if(it.body()!!.success){
                        Toast.makeText(this, it.body()!!.message, Toast.LENGTH_SHORT)
                        finish()
                    }
                    else{
                        Toast.makeText(this, it.body()!!.message, Toast.LENGTH_SHORT)
                    }
                }
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}