package com.example.cozy.views.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var mAuth: FirebaseAuth

    private lateinit var login_view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d(TAG, "로그인 액티비티 생성")
        login_view = findViewById(R.id.login_layout)
        // Configure Google Sign In to request user's email address and profile
        //유저 데이터 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        //Build a GoogleSignInClient w/ the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val googleSingInButton: SignInButton = findViewById(R.id.google_sign_in_button)
        googleSingInButton.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        //이미 로그인한 구글 계정이 있으면 non-null
        var account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(applicationContext)
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.putExtra(GOOGLE_ACCOUNT, account)
            startActivity(intent)
            finish()
            Log.d(TAG, "on Start works successful")
        } else{
            Log.d(TAG, "현재 로그인한 계정이 없는 경우")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSigconsole.firebase.google.comnInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "이름 = " + account.displayName)
                Log.d(TAG, "이메일 = " + account.email)
                Log.d(TAG, "getID() = " + account.id)
                Log.d(TAG, "getAccount() = " + account.account)
                Log.d(TAG, "getIDToken() = " + account.idToken)

                firebaseAuthWithGoogle(account.idToken!!,task)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
            /*if(result!!.isSuccess()){
                val account = result.signInAccount
                Log.d(TAG, "이름 = " + account!!.displayName)
                Log.d(TAG, "이메일 = " + account!!.email)
                Log.d(TAG,"getID() = " + account!!.id)
                Log.d(TAG, "getAccount() = " + account!!.account)
                Log.d(TAG, "getIDToken() = " + account!!.idToken)

            }*/
        }
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String, completeTask: Task<GoogleSignInAccount>) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
//                    val user = mAuth.currentUser
                    val user : GoogleSignInAccount? = completeTask.getResult(ApiException::class.java)
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    val snackbar = Snackbar.make(
                        login_view,
                        "로그인에 실패하였습니다.\n확인을 누르면 사라집니다.",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.setAction("확인", View.OnClickListener {
                        snackbar.dismiss()
                    })
                    snackbar.show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                //hideProgressBar()
                // [END_EXCLUDE]
            }
    }
    // [END auth_with_google]

    private fun updateUI(account: GoogleSignInAccount?) {
        val intent = Intent(applicationContext, ProfileActivity::class.java)
        intent.putExtra(GOOGLE_ACCOUNT, account)
        Log.d(TAG,"프로필 액티비티로 넘어갈 intent의 이메일 주소" + account?.email)
        startActivityForResult(intent, 1001)
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.google_sign_in_button -> {
                signIn()
                Log.d(TAG, "sign in button works")
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun revokeAccess() {
        // Firebase sign out
        mAuth.signOut()

        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(this) {
            updateUI(null)
        }
    }

    companion object {
        private val TAG = "LoginActivityTAG"
        private val RC_SIGN_IN = 9001
    }
}