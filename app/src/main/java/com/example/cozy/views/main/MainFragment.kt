package com.example.cozy.views.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.core.util.Pair
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.example.cozy.ItemDecoration
import com.example.cozy.MainActivity
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.views.SearchActivity
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_recommend.view.*

class MainFragment : Fragment() {

    lateinit var recommendAdapter: RecommendAdapter
    val service = RequestToServer.service
    val recommendData = mutableListOf<RecommendData>()
    private lateinit var userNickname : TextView
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_main, container, false)

        setHasOptionsMenu(true)

        userNickname = view.findViewById(R.id.nickname)
        sharedPref = activity!!.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        userNickname.text = sharedPref.getString("nickname","나그네")

        initRecommend(view)
        return view
    }

    fun initRecommend(v : View){
        recommendAdapter = RecommendAdapter(v.context){RecommendData, View ->
            var intent = Intent(activity as MainActivity,RecommendDetailActivity::class.java)
            intent.putExtra("bookstoreIdx",RecommendData.bookstoreIdx)
            val imageViewPair = Pair.create<View, String>(View.rec_img, View.rec_img.transitionName)
            var option : ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as MainActivity, imageViewPair)
            startActivity(intent, option.toBundle())
        }
        v.rv_recommend.adapter = recommendAdapter
        loadData(v)
    }

    private fun loadData(v : View) {

        val header = mutableMapOf<String, String>()
        header["Content-Type"] = "application/json"
        header["token"] = sharedPref.getString("token", "token").toString()
        if (header["token"] == "token") {
            service.requestRecommendation().customEnqueue(
                onError = {
                    Toast.makeText(context!!, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)
                },
                onSuccess = {
                    if (it.body()!!.success) {
                        Log.d("success message >>>> ", it.body()!!.message)
                        recommendData.clear()
                        recommendData.addAll(it.body()!!.data)
                        recommendAdapter.datas = recommendData
                        v.rv_recommend.addItemDecoration(ItemDecoration(this.context!!, 0, 16))
                        recommendAdapter.notifyDataSetChanged()
                    } else {
                        Log.d("message >>>> ", it.body()!!.message)
                    }
                }
            )
        }
        else{
            service.requestRecommendationUser(header).customEnqueue(
                onError = {
                    Toast.makeText(context!!, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)
                },
                onSuccess = {
                    if (it.body()!!.success) {
                        Log.d("success message >>>> ", it.body()!!.message)
                        recommendData.clear()
                        recommendData.addAll(it.body()!!.data)
                        recommendAdapter.datas = recommendData
                        v.rv_recommend.addItemDecoration(ItemDecoration(this.context!!, 0, 16))
                        recommendAdapter.notifyDataSetChanged()
                    } else {
                        Log.d("message >>>> ", it.body()!!.message)
                    }
                }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search -> {
                val intent = Intent(context, SearchActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) //해보고 이상하면 지울것
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
