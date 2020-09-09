package com.example.cozy

import android.accounts.Account
import android.accounts.AccountManager
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

    private lateinit var callback: SessionCallback
    val requestToServer = RequestToServer
    private lateinit var session : Session

    private lateinit var login_view: View
    private lateinit var token : String

    lateinit var editor : SharedPreferences.Editor

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
//            Log.d(TAG, "id : " + account?.id)
        } else{
            Log.d(TAG, "현재 로그인한 계정이 없는 경우")
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

                val runnable = Runnable {
                    val scope = "oauth2:"+Scopes.EMAIL+" "+Scopes.PROFILE
                    val accessToken: String = GoogleAuthUtil.getToken(
                        applicationContext,
                        account?.account,
                        scope,
                        Bundle()
                    )

                    token = accessToken
                    Log.d(TAG, "accessToken : ${token}")
//                    val accnt = task.getResult(ApiException::class.java)!!
                    requestToServer.service.requestLogin(
                        RequestLogin(
                            id = account?.idToken!!,
                            nickname = account.displayName!!,
                            refreshToken = token
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

                }
                AsyncTask.execute(runnable)
                /*val refreshToken = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestServerAuthCode(account?.id, true)
                    .build()
                val mScope = "oauth2:server:client_id:" + account!!.idToken + ":api_scope:" + "https://www.googleapis.com/auth/userinfo.email"
                val token = GoogleAuthUtil.getToken(this@LoginActivity, account, mScope)
                Log.d(TAG, "리프레시 토큰 : $refreshToken")
                */
//                val token = GoogleAuthUtil.getToken(mActivity, accnt, mScope, Bundle())

                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val accnt = task.getResult(ApiException::class.java)!!

                    /*Log.d(TAG, "이름 = " + accnt.displayName)
                    Log.d(TAG, "이메일 = " + accnt.email)
                    Log.d(TAG, "getID() = " + accnt.id)
                    Log.d(TAG, "getAccount() = " + accnt.account)
                    Log.d(TAG, "getIDToken() = " + accnt.idToken)
                    Log.d(TAG," " )
                    requestToServer.service.requestLogin(
                        RequestLogin(
                            email = account?.email!!,
                            nickname = account?.displayName!!,
                            refreshToken = token
                        )
                    ).customEnqueue(
                        onError = {
                            Log.d(TAG, "server 로그인 정보 전송 error 발생")
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
                                Log.d(TAG, "이름 = " + account?.displayName)
                                Log.d(TAG, "이메일 = " + account?.email)
                            } else
                                Log.d(TAG, "서버 정보 전송 오류 발생.")

//                                val refreshToken = GoogleSignInOptions.Builder().requestServerAuthCode(account?.id)
//                                Log.d(TAG, "리프레시 토큰 구하기" + refreshToken)
                        }
                    )*/

                    firebaseAuthWithGoogle(accnt.idToken!!, task)

                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                    // ...
                }
            }


            /*
                val account = result.signInAccount*/
        }
    }
    //카카오
    public inner class SessionCallback : ISessionCallback {
        override fun onSessionOpened() {
            // 로그인 세션이 열렸을 때
            UserManagement.getInstance().me(object : MeV2ResponseCallback() {
                override fun onSuccess(result: MeV2Response?) {
                    // 로그인이 성공했을 때
                    Log.d(TAG, "카카오 로그인 성공")
                    val kakaoAccount: UserAccount = result!!.kakaoAccount
                    val kakao_email = kakaoAccount.email
                    val nickname = kakaoAccount.profile.nickname
                    val profile_pic = kakaoAccount.profile.profileImageUrl
                    //리프레시 토큰
                    val session = Session.getCurrentSession().tokenInfo.refreshToken

                    //server 통신
                    requestToServer.service.requestLogin(
                        RequestLogin(
                            id = kakao_email,
                            nickname = nickname,
                            refreshToken = session.toString()
                        )
                    ).customEnqueue(
                        onError = {
                            Log.e(TAG, "onError!!!!")
                        },
                        onSuccess = {
                            Log.i("KAKAO_API", "사용자 이름: ${it.data.nickname}");
                            Log.i("KAKAO_API", "사용자 이메일: $kakao_email");
                            Log.i("KAKAO_API", "사용자 토큰: $session");
                            finish()
                        }
                    )
                    Log.i("KAKAO_API", "사용자 토큰: $session");
                    var intent = Intent(this@LoginActivity, MainActivity::class.java) //MainActivity
                    intent.putExtra("kakao_name", nickname)
                    intent.putExtra("kakao_email", kakao_email)
                    intent.putExtra("kakao_picture", profile_pic)
                    startActivity(intent)
                    finish()
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    Log.e("KAKAO_API", "세션이 닫혀 있음: $errorResult")
                    Toast.makeText(
                        this@LoginActivity,
                        "세션이 닫혔습니다. 다시 시도해주세요 : ${errorResult.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
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
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun redirectSignupActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.google_sign_in_button -> {
                signIn()
                Log.d(TAG, "sign in button works")
            }
            R.id.btn_passing_sign_in -> {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        Log.d(TAG, "SignIn함수 호출")
    }

    private fun firebaseAuthWithGoogle(idToken: String, completeTask: Task<GoogleSignInAccount>) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
//                    val user = mAuth.currentUser
                    val user : GoogleSignInAccount? = completeTask.getResult(ApiException::class.java)
                    val authCode : String? = user?.serverAuthCode
//                    val account: GoogleSignInAccount? = result.signInAccount

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
        val intent = Intent(applicationContext, MainActivity::class.java)//ProfileActivity
        intent.putExtra(GOOGLE_ACCOUNT, account)
        Log.d(TAG, "프로필 액티비티로 넘어갈 intent의 이메일 주소" + account?.email)
        startActivityForResult(intent, 1001)
        finish()
    }

    /*private fun getAccessCode(result : GoogleSignInResult?) : String {
        val account: GoogleSignInAccount? = result?.signInAccount

        val runnable = Runnable {
            val scope = "oauth2:" + Scopes.EMAIL + " " + Scopes.PROFILE
            val accessToken: String = GoogleAuthUtil.getToken(
                applicationContext,
                account?.account,
                scope,
                Bundle()
            )
            token = accessToken
        }
        AsyncTask.execute(runnable)

        return token
    }*/
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
//        Session.getCurrentSession().removeCallback(callback);
    }
}