package com.example.cozy.views.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import com.example.cozy.ItemDecoration
import com.example.cozy.MainActivity
import com.example.cozy.R
import com.example.cozy.LoginActivity
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.views.main.RecommendDetailActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.auth.Session
import com.kakao.usermgmt.response.MeV2Response
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_mypage.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import java.lang.Exception

class MypageFragment : Fragment(), View.OnClickListener {

    val service = RequestToServer.service
    lateinit var recentlySeenAdapter: RecentlySeenAdapter
    var data = mutableListOf<RecentlySeenData>()
    lateinit var recentlySeenData: RecentlySeenData
    lateinit var fragView: View
    private val TAG = "MyPageTAG"

    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth

    //카카오
    lateinit var session : Session

    private lateinit var profileImage: CircleImageView
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragView = inflater.inflate(R.layout.fragment_mypage, container, false)

        //이하 구글 계정 로그인 된 것 있는지 가져오는 동작.

        setHasOptionsMenu(true)

        Log.d(TAG, "내 정보 페이지 프래그먼트 뷰 생성.")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity as MainActivity, gso)

        val account: GoogleSignInAccount? =
            GoogleSignIn.getLastSignedInAccount(activity as MainActivity)
        auth = Firebase.auth
        val currentUser = auth.currentUser

        Log.d(TAG, "current user email is = " + auth.currentUser?.email)
        Log.d(TAG, "last signed in account = " + account?.email)

        profileImage = fragView.findViewById(R.id.rounded_iv_profile)
        profileName = fragView.findViewById(R.id.tv_user_name)
        profileEmail = fragView.findViewById(R.id.tv_user_email)

        loadRSData()

        if (currentUser != null) {//user google 로그인 한 상태면 여기로
            fragView.findViewById<View>(R.id.rounded_iv_profile).setOnClickListener(this)

            setDataOnView(account)
        }
        else if (Session.getCurrentSession() != null){ //user kakao 로그인 한 상태면 여기로
            fragView.findViewById<View>(R.id.rounded_iv_profile).setOnClickListener(this)


        }
        else {
            //user 로그인 안 한 상태
            fragView.findViewById<View>(R.id.btn_login).setOnClickListener { View ->
                val intent = Intent(activity as MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        fragView.findViewById<View>(R.id.btn_interests).setOnClickListener(this)
        fragView.findViewById<View>(R.id.view_notice).setOnClickListener(this)
        fragView.findViewById<View>(R.id.view_event).setOnClickListener(this)

        //카카오는 소셜 로그인 후 서버에 토큰, 이름, 이메일, 사진 POST 후 MypageFragment에서 데이터 GET or Firebase..
        return fragView
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "화면 구성 onStart 호출")
        val currentUser = auth.currentUser
        if (currentUser != null) {//구글
            Log.d(TAG, "현재 로그인한 계정이 있음." + FirebaseAuth.getInstance().currentUser?.email)
            Log.d(TAG, "현재 로그인한 계정의 아이디 토큰 " + (auth.currentUser)?.getIdToken(true))
            updateUI()
            setDataOnView(GoogleSignIn.getLastSignedInAccount(activity as MainActivity))
        }
        else if(Session.getCurrentSession() != null){
            updateUI()
            profileName.text = MeV2Response.KEY_NICKNAME
            Picasso.get().load(MeV2Response.KEY_PROFILE_IMAGE)
                .centerInside().fit().into(profileImage)
        }
        else{
            btn_login.visibility = View.VISIBLE
            tv_login_needed.visibility = View.VISIBLE
            rounded_iv_profile.visibility = View.GONE
            tv_user_name.visibility = View.GONE
            tv_user_email.visibility = View.GONE
            rv_recently_seen.visibility = View.GONE //로그인 안 한 유저도 최근 책방 보여준다면 이것 수정해야함.
            tv_no_recently_seen_text.visibility = View.VISIBLE
        }

    }

    private fun updateUI() {
        btn_login.visibility = View.GONE
        tv_login_needed.visibility = View.GONE
        rounded_iv_profile.visibility = View.VISIBLE
        tv_user_name.visibility = View.VISIBLE
        tv_user_email.visibility = View.VISIBLE
        rv_recently_seen.visibility = View.VISIBLE //로그인 안 한 유저도 최근 책방 보여준다면 이것 수정해야함.
        tv_no_recently_seen_text.visibility = View.GONE

        rounded_iv_profile.setOnClickListener(this)//여기 없으면 로그아웃 하고 바로 화면 다시 그린 상황에서 프로필 사진 클릭 안됨.
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rounded_iv_profile -> {
                val intent = Intent(activity as MainActivity, ProfileActivity::class.java)
                intent.putExtra(GOOGLE_ACCOUNT, GoogleSignIn.getLastSignedInAccount(activity))
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
        }
    }

    private fun setDataOnView(account: GoogleSignInAccount?) {
        Picasso.get().load(account?.photoUrl).centerInside().fit().into(profileImage)
        profileName.setText(account?.displayName)
        profileEmail.setText(account?.email)
    }

    fun loadRSData() {
        val sharedPref = activity!!.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val header = mutableMapOf<String, String?>()
        header["Content-Type"] = "application/json"
        header["token"] = sharedPref.getString("token", "token")
        service.requestRecentlySeen(header).customEnqueue(
            onError = { Toast.makeText(context!!, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT).show() },
            onSuccess = {
                if (it.success) {
                    if (it.message == "최근 본 책방 조회 성공") {
                        recentlySeenAdapter = RecentlySeenAdapter(
                            context!!,
                            it.data.toMutableList()
                        ) { RecentlySeenData, View ->
                            val intent = Intent(
                                activity as MainActivity,
                                RecommendDetailActivity::class.java
                            )
                            intent.putExtra("bookstoreIdx", RecentlySeenData.bookstoreIdx)
                            startActivity(intent)
                        }
                        rv_recently_seen.adapter = recentlySeenAdapter
                        rv_recently_seen.addItemDecoration(ItemDecoration(this.context!!, 13, 0))
                        Log.d(TAG, "최근 본 책방 통신 성공.")
                    } else {
                        onEmpty()
                        Log.d(TAG, "최근 본 책방 없음. 통신 성공")
                    }
                }else onEmpty()
            }
        )
        Log.d(TAG, "최근 본 책방 어댑터 적용 됨.")

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.setting, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(this.context, "설정", Toast.LENGTH_SHORT).show()
//                val intent = Intent(context,ProfileActivity::class.java)
//                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onEmpty() {
        rv_recently_seen.visibility = View.GONE
        tv_no_recently_seen_text.visibility = View.VISIBLE
    }
}