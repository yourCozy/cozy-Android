package com.example.cozy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cozy.network.RequestToServer
import com.example.cozy.network.customEnqueue
import com.example.cozy.network.requestData.RequestLogin
import com.example.cozy.onboarding.OnBoardingPreferenceActivity
import com.example.cozy.views.mypage.GOOGLE_ACCOUNT
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.UserAccount
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.log.Logger


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var mAuth: FirebaseAuth

    val requestToServer = RequestToServer
    private lateinit var session : Session

    private lateinit var login_view: View

    lateinit var sharedPref : SharedPreferences
    lateinit var editor: SharedPreferences.Editor

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

        sharedPref = this.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        editor = sharedPref.edit()

        val googleSingInButton: Button = findViewById(R.id.google_sign_in_button)
        googleSingInButton.setOnClickListener(this)

        val kakaoSignInButton : Button = findViewById(R.id.kakao_sign_in_btn)
        kakaoSignInButton.setOnClickListener(View.OnClickListener {
            session = Session.getCurrentSession()
            session.addCallback(SessionCallback())
            session.open(AuthType.KAKAO_LOGIN_ALL, this@LoginActivity)
        })

        val passingButton : Button = findViewById(R.id.btn_passing_sign_in)
        passingButton.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()

    }



    override fun onStart() {
        super.onStart()

        //이미 로그인한 구글 계정이 있으면 non-null
        var account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(applicationContext)
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra(GOOGLE_ACCOUNT, account)
            startActivity(intent)
            finish()
            Log.d(TAG, "on Start works successful")
        } else{
            Log.d(TAG, "현재 로그인한 계정이 없는 경우")
        }

        //이미 로그인한 카카오 계정이 있으면 non-null

        if(Session.getCurrentSession().isOpened){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            Log.d(TAG, "on Start kakao works successful")
            Log.d(TAG,Session.getCurrentSession().toString())
            Log.d(TAG,Session.getCurrentSession().isOpened.toString())
        }
        else{
            Log.d(TAG, "현재 카카오 로그인한 계정이 없는 경우")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //카카오
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSigconsole.firebase.google.comnInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            val result : GoogleSignInResult? = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result!!.isSuccess()) {
                val account: GoogleSignInAccount? = result.signInAccount

                /*val runnable = Runnable {
                    val scope = "oauth2:"+Scopes.EMAIL+" "+Scopes.PROFILE
                    val accessToken: String = GoogleAuthUtil.getToken(
                        applicationContext,
                        account?.account,
                        scope,
                        Bundle()
                    )

                    Log.d(TAG, "accessToken : ${accessToken}")

                }
                AsyncTask.execute(runnable)*/
            }
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
                Toast.makeText(this,"로그인에 실패하셨습니다.",Toast.LENGTH_SHORT).show()
                // ...
            }
        }
    }
    //카카오
    public inner class SessionCallback : ISessionCallback {
        override fun onSessionOpened() {
            // 로그인 세션이 열렸을 때
            UserManagement.getInstance().me( object : MeV2ResponseCallback() {
                override fun onSuccess(result: MeV2Response?) {
                    // 로그인이 성공했을 때
                    Log.d(TAG, "카카오 로그인 성공")
                    val kakaoAccount: UserAccount = result!!.kakaoAccount
                    val kakao_email = kakaoAccount.email
                    val kakao_id = result.id
                    val nickname = kakaoAccount.profile.nickname
                    val profile_pic = kakaoAccount.profile.profileImageUrl
                    //리프레시 토큰
                    val session = Session.getCurrentSession().tokenInfo.refreshToken

                    //server 통신
                    requestToServer.service.requestLogin(
                        RequestLogin(
                            id = kakao_id.toString(),
                            nickname = nickname,
                            refreshToken = session.toString()
                        )
                    ).customEnqueue(
                        onError = {
                            Log.e(TAG, "onError!!!!")
                        },
                        onSuccess = {
                            val data = it.data
                            editor.putString("token",data.jwtToken) // key,value 형식으로 저장
                            editor.putString("nickname",data.nickname)
                            editor.putString("profile",data.profile)
                            editor.apply()
                            editor.commit()    //최종 커밋. 커밋을 해야 저장이 된다.
                            Log.i("KAKAO_API", "사용자 이름: ${it.data.nickname}")
                            Log.i("KAKAO_API", "사용자 이메일: ${it.data.email}")
                            Log.i("KAKAO_API", "사용자 토큰: ${it.data.jwtToken}")
                            finish()
                        }
                    )
                    Log.i("KAKAO_API", "사용자 id: $kakao_id");
                    var intent = Intent(this@LoginActivity, OnBoardingPreferenceActivity::class.java) //MainActivity
                    startActivity(intent)
                    finish()
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    Log.e("KAKAO_API", "세션이 닫혀 있음: $errorResult")
                    Toast.makeText(
                        this@LoginActivity,
                        "세션이 닫혔습니다. 다시 시도해주세요 : ${errorResult.toString()}",
                        Toast.LENGTH_SHORT).show()
                }
            })
        }
        override fun onSessionOpenFailed(exception: KakaoException?) {
            Log.e("SessionCallback :: ", "onSessionOpenFailed : $exception")
            // 로그인 세션이 정상적으로 열리지 않았을 때
            if (exception != null) {
                Logger.e(exception)
                Toast.makeText(
                    this@LoginActivity,
                    "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요 : $exception",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun redirectSignupActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
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
//                    val authCode : String? = user?.serverAuthCode
//                    val account: GoogleSignInAccount? = result.signInAccount
//                    Log.d(TAG, "authCode: $authCode")

                    requestToServer.service.requestLogin(
                        RequestLogin(
                            id = user?.idToken!!,
                            nickname = user.displayName!!,
                            refreshToken = user.idToken!!
                        )
                    ).customEnqueue(
                        onError = {
                            Log.d(TAG, "server 로그인 정보 전송 실패")
                        },
                        onSuccess = {
                            if (it.success) {
                                val data = it.data
                                editor.putString("token", data.jwtToken)
                                editor.putString("email", data.email)
                                editor.putString("nickname", data.nickname)
                                editor.putString("profile",data.profile)
                                editor.apply()
                                editor.commit()
                                Log.d(TAG, "이름 = " + data.nickname)
                                Log.d(TAG, "이메일 = " + data.email)
                                Log.d(TAG, "사용자 토큰 = "+ data.jwtToken)
                            }else{
                                Log.d(TAG, "로그인 정보 서버에 전송하는 건 실패!")
                            }
                        }
                    )
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

            }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        val intent = Intent(applicationContext, OnBoardingPreferenceActivity::class.java)//ProfileActivity
        intent.putExtra(GOOGLE_ACCOUNT, account)
        Log.d(TAG, "프로필 액티비티로 넘어갈 intent의 이메일 주소" + account?.email)
        startActivityForResult(intent, 1001)
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.google_sign_in_button -> {
                signIn()
                Log.d(TAG, "sign in button works")
            }
            R.id.btn_passing_sign_in ->{
                val intent = Intent(this, MainActivity::class.java)
                editor.putString("token","token") // key,value 형식으로 저장
                editor.putString("nickname","나그네")
                editor.apply()
                editor.commit();
                startActivity(intent)
                finish()
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

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(SessionCallback())
    }
}