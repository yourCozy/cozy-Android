package com.example.cozy.views.mypage

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
import com.kakao.usermgmt.response.model.UserAccount
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_mypage.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class MypageFragment : Fragment(), View.OnClickListener {
    lateinit var recentlySeenAdapter: RecentlySeenAdapter
    var data = mutableListOf<RecentlySeenData>()
    private val TAG = "MyPageTAG"

    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth

    private lateinit var profileImage: CircleImageView
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //이하 구글 계정 로그인 된 것 있는지 가져오는 동작.
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

        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        Log.d(TAG, "current user email is = " + auth.currentUser?.email)
        Log.d(TAG, "last signed in account = " + account?.email)


        recentlySeenAdapter = RecentlySeenAdapter(view.context) { RecentlySeenData, View ->
            val intent = Intent(activity as MainActivity, RecommendDetailActivity::class.java)
            startActivity(intent)
            Log.d(TAG, "어댑터 및 클릭리스너 설정됨.")
        }
        with(view.rv_recently_seen) {
            adapter = recentlySeenAdapter
        }
        profileImage = view.findViewById(R.id.rounded_iv_profile)
        profileName = view.findViewById(R.id.tv_user_name)
        profileEmail = view.findViewById(R.id.tv_user_email)

        if (currentUser != null) {//user 로그인 한 상태면 여기로
            loadData(view)

            view.findViewById<View>(R.id.rounded_iv_profile).setOnClickListener(this)

            setDataOnView(account)
        } else {
            //user 로그인 안 한 상태
            view.findViewById<View>(R.id.btn_login).setOnClickListener { View ->
                val intent = Intent(activity as MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        view.findViewById<View>(R.id.btn_interests).setOnClickListener(this)
        view.findViewById<View>(R.id.view_notice).setOnClickListener(this)
        view.findViewById<View>(R.id.view_event).setOnClickListener(this)

        //카카오는 소셜 로그인 후 서버에 토큰, 이름, 이메일, 사진 POST 후 MypageFragment에서 데이터 GET or Firebase..

        return view

    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "화면 구성 onStart 호출")
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d(TAG, "현재 로그인한 계정이 있음." + FirebaseAuth.getInstance().currentUser?.email)
            Log.d(TAG, "현재 로그인한 계정의 아이디 토큰 " + (auth.currentUser)?.getIdToken(true))
        }
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {//로그인 된 상태

            btn_login.visibility = View.GONE
            tv_login_needed.visibility = View.GONE
            rounded_iv_profile.visibility = View.VISIBLE
            tv_user_name.visibility = View.VISIBLE
            tv_user_email.visibility = View.VISIBLE
            rv_recently_seen.visibility = View.VISIBLE //로그인 안 한 유저도 최근 책방 보여준다면 이것 수정해야함.
            tv_no_recently_seen_text.visibility = View.GONE

            loadData(view!!)
            rounded_iv_profile.setOnClickListener(this)//여기 없으면 로그아웃 하고 바로 화면 다시 그린 상황에서 프로필 사진 클릭 안됨.
            setDataOnView(GoogleSignIn.getLastSignedInAccount(activity as MainActivity))
            /*
                if (user.isEmailVerified) {
                } else {
                }
            */
        } else {
            btn_login.visibility = View.VISIBLE
            tv_login_needed.visibility = View.VISIBLE
            rounded_iv_profile.visibility = View.GONE
            tv_user_name.visibility = View.GONE
            tv_user_email.visibility = View.GONE
            rv_recently_seen.visibility = View.GONE //로그인 안 한 유저도 최근 책방 보여준다면 이것 수정해야함.
            tv_no_recently_seen_text.visibility = View.VISIBLE

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rounded_iv_profile -> {
                val intent = Intent(activity as MainActivity, ProfileActivity::class.java)
                intent.putExtra(GOOGLE_ACCOUNT, GoogleSignIn.getLastSignedInAccount(activity))
                startActivity(intent)
            }
            R.id.btn_interests -> {
                val intent = Intent(activity as MainActivity, NoInterestsActivity::class.java)
                startActivity(intent)
            }
            R.id.view_notice -> {
                Toast.makeText(context, "공지사항을 선택했습니다.", Toast.LENGTH_SHORT).show()
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

    fun loadData(v: View) {
        try {
            data.apply {
                for (i in 0..3) {
                    add(
                        RecentlySeenData(
                            i,
                            "ex_socialcut_unsplash",
                            "보라돌이의 책방"
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("CATCH", "data load failed")
        }

        recentlySeenAdapter.data = data
        v.rv_recently_seen.addItemDecoration(ItemDecoration(this.context!!, 13, 0))
        recentlySeenAdapter.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.setting, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(this.context, "설정", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
