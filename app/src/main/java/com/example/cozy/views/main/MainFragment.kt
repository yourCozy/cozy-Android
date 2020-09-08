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
    lateinit var auth: FirebaseAuth
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
        //로그인한 상태에서 setDataOnView
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {//user google 로그인 한 상태면 여기로

            setDataOnView(currentUser)
        } /*else if(kakao 로그인){

        } else{ // 로그인 안 한 상태.

        }*/
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

    private fun loadData(v : View){

        val header = mutableMapOf<String, String>()
        header["Content-Type"] = "application/json"
//        header["token"] = sharedPref.getString("token","token").toString()
        header["token"] = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4Ijo4LCJpYXQiOjE1OTk1NDUwODMsImV4cCI6MTU5OTU4MTA4MywiaXNzIjoib3VyLXNvcHQifQ.5LiwFhnFJ-zLcuafwaGzHtjdlxIlM13sXgXdnb_G7q8"
        service.requestRecommendation(header).customEnqueue(
            onError = {
                Toast.makeText(context!!, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)
            },
            onSuccess = {
                if(it.success) {
                    Log.d("success message >>>> ",it.message)
                    recommendData.clear()
                    recommendData.addAll(it.data)
                    recommendAdapter.datas = recommendData
                    v.rv_recommend.addItemDecoration(ItemDecoration(this.context!!, 0, 16))
                    recommendAdapter.notifyDataSetChanged()
                }
                else{
                    Log.d("message >>>> ",it.message)
                }
            }
        )
    }

    private fun setDataOnView(account: FirebaseUser?) {
        userNickname.setText(account?.displayName)
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
