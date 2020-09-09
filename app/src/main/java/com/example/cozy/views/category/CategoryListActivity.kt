package com.example.cozy.views.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.views.main.event.EventDetailActivity
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlin.properties.Delegates

class CategoryListActivity : AppCompatActivity() {

    val service = RequestToServer.service
    private lateinit var categoryAdapter: CategoryAdapter
    var data = mutableListOf<CategoryData>()
    lateinit var activityData: CategoryData
    var categoryIdx by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        if (intent.hasExtra("categoryIdx")) {
            categoryIdx = intent.getIntExtra("categoryIdx", 0)
        }

        setSupportActionBar(tb_event)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)

        tb_event.elevation = 5F
        loadCategoryActivityData()
    }

    override fun onResume() {
        super.onResume()

    }

    fun loadCategoryActivityData() {
        val sharedPref = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val header = mutableMapOf<String, String?>()
        header["Context-Type"] = "application/json"
        header["token"] = sharedPref.getString("token", "token")
        service.requestCategoryActivity(categoryIdx, header).customEnqueue(
            onError = {
                Toast.makeText(applicationContext, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                if (it.success) {

                        categoryAdapter =
                            CategoryAdapter(this, it.data.toMutableList()) { CategoryData, View ->
                                val intent = Intent(this, EventDetailActivity::class.java)
                                //여기 사이에 activityIdx 누른 것을 어떻게 받아오지??
                                intent.putExtra("activityIdx", CategoryData.activityIdx)
                                startActivity(intent)
                                Log.d("CATEGORY_READ", "카테고리 별 활동 읽기 성공")
                            }
                        rv_event_item_list.adapter = categoryAdapter

                }else onEmpty()
            }
        )
        rv_event_item_list.addItemDecoration(ItemDecoration(this, 0, 32))
//        categoryAdapter.notifyDataSetChanged() Log.e("CATCH", "notifydatasetChanged 동작함")

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

    fun onEmpty(){
        Log.d("CATEGORY_READ", "진행 중인 활동이 없어요.")
    }
}