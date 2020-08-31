package com.example.cozy.views.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.MainActivity
import com.example.cozy.R
import com.example.cozy.views.main.MainFragment
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.activity_myaccount.*
import com.google.firebase.ktx.Firebase

private lateinit var mGoogleSignInAccount: GoogleSignInAccount
private lateinit var mGoogleSignInClient: GoogleSignInClient
private lateinit var profileName: TextView
private lateinit var profileEmail: TextView
private lateinit var profileImage: ImageView
val GOOGLE_ACCOUNT: String = "google_account"
private val TAG = "ProfileActivityTAG"

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        Log.d(TAG, "클라이언트가 잘 받아졌는지 확인." + mGoogleSignInClient.api)


        profileName = findViewById(R.id.edit_detail_username)
        profileEmail = findViewById(R.id.edit_detail_email)
        profileImage = findViewById(R.id.rounded_iv_account_detail_profile)

        btn_account_detail_logout.setOnClickListener(this)



//        "여기로 보내는 액티비티마다 intent.putExtra해야함."
        mGoogleSignInAccount = intent.getParcelableExtra(GOOGLE_ACCOUNT)

        setDataOnView()
    }

    private fun setDataOnView(){
        Picasso.get().load(mGoogleSignInAccount.photoUrl).centerInside().fit().into(profileImage)
        profileName.setText(mGoogleSignInAccount.displayName)
        profileEmail.setText(mGoogleSignInAccount.email)
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
        Firebase.auth.signOut()
        mGoogleSignInClient.signOut().addOnCompleteListener(this){
            val user = FirebaseAuth.getInstance().currentUser
            user?.unlink(user.providerId)

            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            Log.d(TAG, "로그아웃. 성공")
            Toast.makeText(applicationContext, "로그아웃 되었습니다.",Toast.LENGTH_SHORT).show()
            startActivity(intent)
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
