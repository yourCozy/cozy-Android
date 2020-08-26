package com.example.cozy.views.main

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cozy.BottomItemDecoration
import com.example.cozy.MainActivity
import com.example.cozy.R
import kotlinx.android.synthetic.main.fragment_main.view.*
import com.example.cozy.views.main.event.EventActivity
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    lateinit var recommendAdapter: RecommendAdapter
    val datas = mutableListOf<RecommendData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_main, container, false)

        setHasOptionsMenu(true)
        initRecommend(view)

        return view
    }

    fun initRecommend(v : View){
        recommendAdapter = RecommendAdapter(v.context){RecommendData, View ->
            var intent = Intent(activity as MainActivity,RecommendDetailActivity::class.java)
            startActivity(intent)
        }
        v.rv_recommend.adapter = recommendAdapter
        loadData(v)
    }

    private fun loadData(v : View){
        datas.apply{
            for ( i in 0..7) {
                add(
                    RecommendData(
                        bookstoreIdx = 1,
                        profile = "ㅎㅎ",
                        tag1 = "이국적인",
                        tag2 = "이국적인",
                        tag3 = "이국적인",
                        intro = "빵과 함께하는 달콤한 책 그리고 오늘",
                        bookstoreName = "홍철책빵",
                        location = "서울특별시 용산구 한강대로102길 5"
                    )
                )
            }
        }
        recommendAdapter.datas = datas
        v.rv_recommend.addItemDecoration(BottomItemDecoration(this.context!!, 16))
        recommendAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search -> Toast.makeText(context,"검색",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
