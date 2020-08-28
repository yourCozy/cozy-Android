package com.example.cozy.views.main.event

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cozy.ItemDecoration
import com.example.cozy.R
import com.example.cozy.views.main.RecommendDetailActivity
import kotlinx.android.synthetic.main.fragment_activity.view.*

class ActivityFragment : Fragment() {

    lateinit var culturalActivityAdapter: CulturalActivityAdapter
    val datas = mutableListOf<CulturalActivityData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_activity, container, false)

        initActivity(view)

        return view
    }

    private fun initActivity(view: View) {
        culturalActivityAdapter =
            CulturalActivityAdapter(view.context) { CulturalActivityData, View ->
                var intent = Intent(
                    activity as RecommendDetailActivity,
                    EventActivity::class.java
                )
                startActivity(intent)
            }
        view.rv_activity.adapter = culturalActivityAdapter
        loadData(view)
    }

    private fun loadData(view: View) {
        datas.apply{
            for (i in 0..7) {
                add(
                    CulturalActivityData(
                        day = "D-3",
                        img = "gg",
                        activity_title = "책방 영화관",
                        activiry_info = "홍철책방을 대표하는 책방 영화관이 돌아왔습니다:-)",
                        activity_price = "16000원"
                    )
                )
            }
        }
        culturalActivityAdapter.datas = datas
        view.rv_activity.addItemDecoration(ItemDecoration(this.context!!, 0,40))
        culturalActivityAdapter.notifyDataSetChanged()
    }

}
