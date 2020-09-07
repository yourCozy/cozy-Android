package com.example.cozy

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)  // custom하기 위해
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)   // 뒤로가기 버튼
        main_toolbar.elevation = 5F

        main_viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        main_viewPager.offscreenPageLimit = 2
        main_viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // 네비게이션 메뉴 아이템 체크
                navigation.menu.getItem(position).isChecked = true

            }

        })

        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_main -> main_viewPager.currentItem = 0
                R.id.menu_map -> main_viewPager.currentItem = 1
                R.id.menu_category -> main_viewPager.currentItem = 2
                R.id.menu_mypage -> main_viewPager.currentItem = 3
            }
            true
        }

        //카카오 해시키 가져오기
        getAppKeyHash()

        //google accesstoken 가져오기
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()



        //google account 이용중인지 확인
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null) {
            currentUser.getIdToken(true)
                .addOnCompleteListener(OnCompleteListener<GetTokenResult>(){
                    fun onComplete(task : Task<GetTokenResult>){
                        if(task.isSuccessful()){
                            val idToken = (task.result)?.token
                            //token 백엔드로 보낼 수 있음.

                        } else{
                            //handle error

                        }
                    }
                })
            //Firebase 실시간 데이터베이스에서 메타데이터 업데이트
//            var ref: DatabaseReference = FirebaseDatabase.getInstance()

        }
    }

    private fun getAppKeyHash() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.d("Hash key", something)
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString())
        }

    }

}
