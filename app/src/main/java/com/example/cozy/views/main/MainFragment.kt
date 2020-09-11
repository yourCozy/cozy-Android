package com.example.cozy.views.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cozy.ItemDecoration
import com.example.cozy.MainActivity
import com.example.cozy.R
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_main.view.*

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
            startActivity(intent)
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
                    if (it.success) {
                        Log.d("success message >>>> ", it.message)
                        recommendData.clear()
                        recommendData.addAll(it.data)
                        recommendAdapter.datas = recommendData
                        v.rv_recommend.addItemDecoration(ItemDecoration(this.context!!, 0, 16))
                        recommendAdapter.notifyDataSetChanged()
                    } else {
                        Log.d("message >>>> ", it.message)
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
                    if (it.success) {
                        Log.d("success message >>>> ", it.message)
                        recommendData.clear()
                        recommendData.addAll(it.data)
                        recommendAdapter.datas = recommendData
                        v.rv_recommend.addItemDecoration(ItemDecoration(this.context!!, 0, 16))
                        recommendAdapter.notifyDataSetChanged()
                    } else {
                        Log.d("message >>>> ", it.message)
                    }
                }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search -> Toast.makeText(context,"검색",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
