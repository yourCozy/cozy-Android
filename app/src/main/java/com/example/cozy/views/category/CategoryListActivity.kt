package com.example.cozy.views.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.views.main.event.EventDetailActivity
import kotlinx.android.synthetic.main.activity_event_list.*

class CategoryListActivity : AppCompatActivity() {
    lateinit var categoryAdapter: CategoryAdapter
    var data = mutableListOf<CategoryData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        setSupportActionBar(tb_event)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)

        tb_event.elevation = 5F

        //eventAdapter = ActivityAdapter()
        categoryAdapter = CategoryAdapter(view_sortByDDay.context) { ActivityData, View ->
            val intent = Intent(this, EventDetailActivity::class.java)
            startActivity(intent)
            Log.e("CATCH", "adapter set success")
        }
        rv_event_item_list.adapter = categoryAdapter
        loadData()

    }

    fun loadData() {
        data.apply {
            add(
                CategoryData(
                    bookstoreIdx = 1,
                    profile = "ex_img_activity_unsplash",
                    activity_name = "책빵영화관",
                    bookstore_name = "코지책방",
                    categoryIdx = "movie",
                    price = "18000원",
                    deadline = "D-3"
                )
            )
            add(
                CategoryData(
                    bookstoreIdx = 1,
                    profile = "",
                    activity_name = "라라랜드",
                    bookstore_name = "코지책방",
                    categoryIdx = "movie",
                    price = "18000원",
                    deadline = "D-3"
                )
            )
            add(
                CategoryData(
                    bookstoreIdx = 1,
                    profile = "",
                    activity_name = "인셉션",
                    bookstore_name = "코지책방",
                    categoryIdx = "movie",
                    price = "18000원",
                    deadline = "D-3"
                )
            )
        }
        categoryAdapter.data = data
        rv_event_item_list.addItemDecoration(ItemDecoration(this, 0, 32))
        categoryAdapter.notifyDataSetChanged()
        Log.e("CATCH", "notifydatasetChanged 동작함")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}