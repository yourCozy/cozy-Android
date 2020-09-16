package com.example.cozy.views.mypage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cozy.ItemDecoration
import com.example.cozy.MainActivity
import com.example.cozy.R
import com.example.cozy.LoginActivity
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.views.main.RecommendDetailActivity
import com.example.cozy.views.mypage.Interest.InterestsActivity
import com.example.cozy.views.mypage.Notice.NoticeActivitiy
import kotlinx.android.synthetic.main.fragment_mypage.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*

class MypageFragment : Fragment(), View.OnClickListener {

    val service = RequestToServer.service
    lateinit var recentlySeenAdapter: RecentlySeenAdapter
    var data = mutableListOf<RecentlySeenData>()
    lateinit var sharedPref: SharedPreferences
    lateinit var fragView: View
    private val TAG = "MyPageTAG"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragView = inflater.inflate(R.layout.fragment_mypage, container, false)

        setHasOptionsMenu(true)

        Log.d(TAG, "내 정보 페이지 프래그먼트 뷰 생성.")

        sharedPref = activity!!.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)

        fragView.rounded_iv_profile.setOnClickListener(this)
        fragView.btn_interests.setOnClickListener(this)
        fragView.view_notice.setOnClickListener(this)
        fragView.view_event.setOnClickListener(this)
        fragView.btn_login.setOnClickListener(this)

        return fragView
    }

    override fun onResume() {
        super.onResume()
        loadMypage()
        rv_recently_seen.addItemDecoration(ItemDecoration(this.context!!, 13, 0))
    }

    private fun loadMypage() {
        val header = mutableMapOf<String, String?>()
        header["Content-Type"] = "application/json"
        header["token"] = sharedPref.getString("token", "token")
        if(header["token"] == "token"){
            fragView.btn_login.visibility = View.VISIBLE
            fragView.tv_login_needed.visibility = View.VISIBLE
            fragView.btn_interests.visibility = View.GONE
            fragView.rounded_iv_profile.visibility = View.GONE
            fragView.tv_user_name.visibility = View.GONE
            fragView.tv_user_email.visibility = View.GONE
            loadRSData()
        }
        else{
            val header = mutableMapOf<String, String?>()
            header["Content-Type"] = "application/json"
            header["token"] = sharedPref.getString("token", "token")
            service.requestMypage(header).customEnqueue(
                onError = {Toast.makeText(context,"올바르지 못한 요청입니다.",Toast.LENGTH_SHORT).show()},
                onSuccess = {
                    if(it.body()!!.success){
                        Log.d(TAG,"성공 message >> ${it.body()!!.message}")
                        fragView.btn_login.visibility = View.GONE
                        fragView.tv_login_needed.visibility = View.GONE
                        fragView.rounded_iv_profile.visibility = View.VISIBLE
                        fragView.tv_user_name.visibility = View.VISIBLE
                        fragView.tv_user_email.visibility = View.VISIBLE
                        val data = it.body()!!.data.elementAt(0)
                        tv_user_name.text = data.nickname
                        Glide.with(this).load(data.profileImg).into(rounded_iv_profile)
                        loadRSData()
                    }else{
                        Log.d(TAG,"실패 message >> ${it.body()!!.message}")
                    }
                }
            )
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "화면 구성 onStart 호출")

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rounded_iv_profile -> {
                val intent = Intent(activity as MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_interests -> {
                val intent = Intent(activity as MainActivity, InterestsActivity::class.java)
                startActivity(intent)
            }
            R.id.view_notice -> {
                val intent = Intent(activity as MainActivity, NoticeActivitiy::class.java)
                startActivity(intent)
            }
            R.id.view_event -> {
                Toast.makeText(context, "이벤트를 선택했습니다.", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_login -> {
                val intent = Intent(activity as MainActivity, LoginActivity::class.java)
                intent.putExtra("fromMypage",true)
                startActivityForResult(intent, 100)
            }
        }
    }


    fun loadRSData() {
        val header = mutableMapOf<String, String?>()
        header["Content-Type"] = "application/json"
        val cookie = sharedPref.getString("Cookie","cookie").toString()
        if(cookie != "cookie") {
            header["Cookie"] = cookie
        }
        service.requestRecentlySeen(header).customEnqueue(
            onError = { Toast.makeText(context!!, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT).show() },
            onSuccess = {
                if (it.body()!!.success) {
                    if (it.body()!!.message == "최근 본 책방 조회 성공") {
                        recentlySeenAdapter = RecentlySeenAdapter(
                            context!!,
                            it.body()!!.data.toMutableList()
                        ) { RecentlySeenData, View ->
                            val intent = Intent(
                                activity as MainActivity,
                                RecommendDetailActivity::class.java
                            )
                            intent.putExtra("bookstoreIdx", RecentlySeenData.bookstoreIdx)
                            startActivity(intent)
                        }
                        rv_recently_seen.adapter = recentlySeenAdapter
                        rv_recently_seen.visibility = View.VISIBLE
                        tv_no_recently_seen_text.visibility = View.GONE
                        Log.d(TAG, "최근 본 책방 통신 성공.")
                    } else {
                        onEmpty()
                        Log.d(TAG, it.body()!!.message)
                    }
                }else {
                    onEmpty()
                    Log.d(TAG, it.body()!!.message)
                }
            }
        )
        Log.d(TAG, "최근 본 책방 어댑터 적용 됨.")

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val user = sharedPref.getString("token", "token")
        if(user != "token") {
            inflater.inflate(R.menu.setting, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(this.context, "설정", Toast.LENGTH_SHORT).show()
                val intent = Intent(context,ProfileActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100){
            loadMypage()
        }
    }

    fun onEmpty() {
        rv_recently_seen.visibility = View.GONE
        tv_no_recently_seen_text.visibility = View.VISIBLE
    }
}