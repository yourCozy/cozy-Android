package com.example.cozy.views.mypage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cozy.LoginActivity
import com.example.cozy.MainActivity
import com.example.cozy.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.auth.Session
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import kotlinx.android.synthetic.main.activity_myaccount.*


private lateinit var mGoogleSignInAccount: GoogleSignInAccount
private lateinit var mGoogleSignInClient: GoogleSignInClient
private lateinit var profileName: TextView
private lateinit var profileEmail: TextView
private lateinit var profileImage: ImageView
val GOOGLE_ACCOUNT: String = "google_account"
private val TAG = "ProfileActivityTAG"

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth

    private lateinit var session : Session
    private lateinit var callback: LoginActivity.SessionCallback

    lateinit var sharedPref: SharedPreferences
    lateinit var nickname: String
    lateinit var profileImg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myaccount)

        setSupportActionBar(tb_myaccount)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)

        tb_myaccount.elevation = 5F

        sharedPref = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        Log.d(TAG, "클라이언트가 잘 받아졌는지 확인." + mGoogleSignInClient.api)

        auth = Firebase.auth
        val currentUser = auth.currentUser


        profileName = findViewById(R.id.edit_detail_username)
        profileEmail = findViewById(R.id.edit_detail_email)
        profileImage = findViewById(R.id.rounded_iv_account_detail_profile)

        setDataOnView()

        btn_account_detail_logout.setOnClickListener(this)

    }

    private fun setDataOnView(){
        Glide.with(this).load(sharedPref.getString("profile", "token")).into(profileImage)
        profileName.text = sharedPref.getString("nickname", "token")
//        profileEmail.text = mGoogleSignInAccount.email
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_account_detail_logout -> {
                Log.d(TAG, "로그아웃 버튼 클릭")
                signOut()
            }
        }
    }

    private fun signOut(){
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null){
            Firebase.auth.signOut()
            mGoogleSignInClient.signOut().addOnCompleteListener(this) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.unlink(user.providerId)

                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                Log.d(TAG, "로그아웃. 성공")
                Toast.makeText(applicationContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        }
        else{
            Log.d(TAG, "카카오 로그아웃")
            UserManagement.getInstance()
                .requestLogout(object : LogoutResponseCallback() {
                    override fun onCompleteLogout() {
                        Log.d(TAG, "카카오 로그아웃 logout")
                        val intent = Intent(this@ProfileActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                })
        }
//        mAuth.signOut()//파이어베이스 signout

        /*googleSignInClient.signOut().addOnCompleteListener(this){
            updateUI()
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(applicationContext, MypageFragment::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
