package com.example.cozy.views.mypage.interest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.views.main.RecommendDetailActivity
import kotlinx.android.synthetic.main.activity_interest.*
import kotlinx.android.synthetic.main.item_interest.*

class InterestActivity : AppCompatActivity(){

    lateinit var interestAdapter : InterestAdapter
    var data = mutableListOf<InterestData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest)

        Log.d("작동하는가?", "dd")
        setSupportActionBar(interest_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        interest_toolbar.elevation = 5F

        interestAdapter = InterestAdapter(this)
   {
           InterestData, View -> val intent = Intent(this@InterestActivity, RecommendDetailActivity::class.java)
       startActivity(intent)
   }

   rv_interest.adapter = interestAdapter
   loadData()
}

private fun loadData() {
   data.apply {
       for ( i in 0..3){
           add(
               InterestData(
                   bookstoreIdx = 1,
                   img = "https://cdn.pixabay.com/photo/2015/11/19/21/11/knowledge-1052014__340.jpg",
                   bookstoreName = "홍철책방",
                   location = "서울특별시 용산구 한강대로102길 5",
                   tag1 = "베이커리",
                   tag2 = "베이커리",
                   tag3 = "베이커리",
                   intro = "매일 새로 구운 빵과 함께하는 달콤한 책 그리고 오늘, 봄날의 책방"
               )
           )
       }
   }
   Log.d("작동하는가?", "dd")
   interestAdapter.data = data
   rv_interest.addItemDecoration(ItemDecoration(this, 0,32))
   interestAdapter.notifyDataSetChanged()
}





override fun onOptionsItemSelected(item: MenuItem): Boolean {
   when(item.itemId){
       android.R.id.home -> finish()
       R.id.search -> Toast.makeText(this,"검색", Toast.LENGTH_SHORT).show()
   }
   return super.onOptionsItemSelected(item)
}

}