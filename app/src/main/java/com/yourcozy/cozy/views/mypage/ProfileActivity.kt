package com.yourcozy.cozy.views.mypage

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.yourcozy.cozy.signin.LoginActivity
import com.yourcozy.cozy.R
import com.yourcozy.cozy.views.mypage.setting.VersionInfoActivity
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
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.views.mypage.profile.*
import kotlinx.android.synthetic.main.activity_myaccount.*


private lateinit var mGoogleSignInClient: GoogleSignInClient
private lateinit var profileName: TextView

private lateinit var profileEmail: TextView
private lateinit var profileImage: ImageView
val GOOGLE_ACCOUNT: String = "google_account"
private val TAG = "ProfileActivityTAG"

private const val REQUEST_PERMISSION_CODE = 100

class ProfileActivity : AppCompatActivity(), View.OnClickListener, DialogInterface.OnDismissListener {
    val service = RequestToServer.service
    lateinit var auth: FirebaseAuth

    private lateinit var session: Session
    private lateinit var callback: LoginActivity.SessionCallback

    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
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
        editor = sharedPref.edit()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        Log.d(TAG, "클라이언트가 잘 받아졌는지 확인." + mGoogleSignInClient.api)

        auth = Firebase.auth
        val currentUser = auth.currentUser


        profileName = findViewById(R.id.edit_detail_username)
        profileEmail = findViewById(R.id.edit_detail_pwd)
        profileImage = findViewById(R.id.rounded_iv_account_detail_profile)

//        setDataOnView()

        btn_profile_change.setOnClickListener(this)
        btn_account_detail_logout.setOnClickListener(this)
        btn_version_info.setOnClickListener(this)
        btn_privacy.setOnClickListener(this)
        btn_edit_username.setOnClickListener(this)
        btn_edit_pwd.setOnClickListener(this)
        kakao_plus.setOnClickListener(this)

    }

    private fun setDataOnView() {
        val header = mutableMapOf<String, String?>()
        header["Content-Type"] = "application/json"
        header["token"] = sharedPref.getString("token", "token")
            service.requestMypageDetail(header).customEnqueue(
                onError = {
                    Toast.makeText(applicationContext, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT).show()
                },
                onSuccess = {
                    if (it.body()!!.success) {
//                        Log.d(TAG, "프로필조회성공>>> ${it.body()!!.message}")
                        val data = it.body()!!.data
                        if(data.checked == 1){//소셜 사용자

                            profileName.text = data.nickname
                            Glide.with(this).load(data.profileImg).into(profileImage)
                            edit_detail_pwd.setInputType(InputType.TYPE_CLASS_TEXT)
                            if(auth.currentUser!= null){
                                tv_account_detail_pwd.text = "이메일"

                                profileEmail.text = (auth.currentUser)!!.email.toString()
                            }else{
                                tv_account_detail_pwd.text = "계정"
                                profileEmail.text = "카카오 계정으로 로그인 하셨습니다."
                                btn_edit_pwd.visibility = View.GONE
                            }

                        }else{//로컬 사용자
                            Glide.with(this).load(data.profileImg).into(profileImage)
                            profileName.text = data.nickname
                            btn_edit_pwd.visibility = View.GONE//나중에 비밀번호 변경 서버 고쳐지면 이거 지우기
                        }
                    } else {
                        Log.d(TAG, "프로필상세조회실패 >> ${it.body()!!.message}")
                        Toast.makeText(applicationContext, "올바르지 않은 요청입니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            )
//        }

    }

    override fun onResume() {
        super.onResume()
        setDataOnView()
    }

    private fun getAllImages(limit: Int? = null, offset: Int? = null): MutableList<ItemPhoto> {
        val cursor: Cursor?
        val columnIndexData: Int
        val order = MediaStore.Video.Media.DATE_TAKEN
        val sortOrder =
            if (limit == null) "$order DESC"
            else "$order DESC LIMIT $limit OFFSET $offset"

        val photoLists: MutableList<ItemPhoto> = mutableListOf()
        val absolutePathOfImg: String? = null
        val uri: Uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )

        cursor = contentResolver.query(
            uri, projection, null, null, sortOrder
        )

        if (cursor != null) {
            columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            while (cursor.moveToNext()) {
                val imageDataPath = cursor.getString(columnIndexData)
                photoLists.add(ItemPhoto(imageDataPath))
            }
        }

        return photoLists
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_account_detail_logout -> {
                Log.d(TAG, "로그아웃 버튼 클릭")
                val customDialog = DialogLogout(this@ProfileActivity)

                customDialog.setOnOKClickedListener {
                    Log.d(TAG, "로그아웃 시도")
                    signOut()
                }

                customDialog.callDialog()

            }
            R.id.btn_version_info -> {
                val intent = Intent(applicationContext, VersionInfoActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_privacy -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://cozybookpaltform.creatorlink.net/")
                startActivity(intent)
            }
            R.id.btn_profile_change -> {
                checkPermission()
//                val intent = Intent(applicationContext, ProfileSetActivity::class.java)
//                startActivity(intent)
            }
            R.id.btn_edit_username ->{
                if(auth.currentUser != null || Session.getCurrentSession().isOpened){
                    val customDialog = DialogSocial(this@ProfileActivity)
                    customDialog.call()
                }else{
                    val customDialog = DialogName(this@ProfileActivity)
                    customDialog.setOnDismissListener(this)
                    customDialog.callDialog(this)
                    setDataOnView()
                }
            }
            R.id.btn_edit_pwd -> {
                if(auth.currentUser != null || Session.getCurrentSession().isOpened){
                    val customDialog = DialogSocial(this@ProfileActivity)
                    customDialog.call()
                }else{
                    val customDialog = DialogChangePwd(this@ProfileActivity)
                    customDialog.callDialog(this)
                }
            }
            R.id.kakao_plus -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_xmxjgCK"))
                startActivity(intent)
            }
        }
    }

    private fun signOut() {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Firebase.auth.signOut()
            mGoogleSignInClient.signOut().addOnCompleteListener(this) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.unlink(user.providerId)
                editor.remove("token")
                editor.remove("nickname")
                editor.remove("profile")
                editor.apply()
                editor.commit()
                Log.d(TAG, "로그아웃. 성공")
                Toast.makeText(applicationContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Log.d(TAG, "카카오 로그아웃")
            UserManagement.getInstance()
                .requestLogout(object : LogoutResponseCallback() {
                    override fun onCompleteLogout() {
                        Log.d(TAG, "카카오 로그아웃 logout")
                        editor.remove("token")
                        editor.remove("nickname")
                        editor.remove("profile")
                        editor.apply()
                        editor.commit()
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

    private fun checkPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(applicationContext, ProfileSetActivity::class.java)
            startActivity(intent)
        } else {
            requestPermission()
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(applicationContext, "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(applicationContext, "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(applicationContext, ProfileSetActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "기능 사용을 위한 권한 동의가 필요합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDismiss(p0: DialogInterface?) {
//        Log.d(TAG,">>>>>>뷰 다시 그리기 확인!!")
//        setDataOnView()
    }


}
