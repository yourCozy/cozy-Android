package com.yourcozy.cozy.views.main.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.yourcozy.cozy.R
import kotlinx.android.synthetic.main.activity_simple_review.*

class SimpleReviewActivity : AppCompatActivity() {

    var facility: String? = null
    var book: String? = null
    var book_activity: String? = null
    var dessert: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_review)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.elevation = 5F

        rg_facility.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.facility_bad -> facility = "별로예요"
                R.id.facility_soso -> facility = "보통이에요"
                R.id.facility_good -> facility = "만족해요"
            }
            if( facility != null && book != null && book_activity != null && dessert != null){
                btn_completion.isEnabled = true
            }
        }

        rg_book.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.book_bad -> book = "별로예요"
                R.id.book_soso -> book = "보통이에요"
                R.id.book_good -> book = "만족해요"
            }
            if( facility != null && book != null && book_activity != null && dessert != null){
                btn_completion.isEnabled = true
            }
        }

        rg_activity.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.activity_bad -> book_activity = "별로예요"
                R.id.activity_soso -> book_activity = "보통이에요"
                R.id.activity_good -> book_activity = "만족해요"
                R.id.activity_no -> book_activity = "해당없음"
            }
            if( facility != null && book != null && book_activity != null && dessert != null){
                btn_completion.isEnabled = true
            }
        }

        rg_dessert.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.dessert_bad -> dessert = "별로예요"
                R.id.dessert_soso -> dessert = "보통이에요"
                R.id.dessert_good -> dessert = "만족해요"
                R.id.dessert_no -> dessert = "해당없음"
            }
            if( facility != null && book != null && book_activity != null && dessert != null){
                btn_completion.isEnabled = true
            }
        }

        btn_completion.setOnClickListener {
            Toast.makeText(this, facility + book + book_activity + dessert, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}