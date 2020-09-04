package com.example.cozy.views.main

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cozy.ItemDecoration
import com.example.cozy.MainActivity
import com.example.cozy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var recommendAdapter: RecommendAdapter
    private lateinit var userNickname : TextView
    val datas = mutableListOf<RecommendData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_main, container, false)

        setHasOptionsMenu(true)
        initRecommend(view)

        userNickname = view.findViewById(R.id.nickname)
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
                        intro1 = "빵과 함께하는",
                        intro2 = "달콤한 책 그리고 오늘",
                        bookstoreName = "홍철책빵",
                        location = "서울특별시 용산구 한강대로102길 5"
                    )
                )
            }
        }
        recommendAdapter.datas = datas
        v.rv_recommend.addItemDecoration(ItemDecoration(this.context!!, 0,16))
        recommendAdapter.notifyDataSetChanged()
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
