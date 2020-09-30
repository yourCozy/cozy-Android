package com.yourcozy.cozy.views.mypage

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
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
import com.yourcozy.cozy.views.mypage.profile.ItemPhoto
import com.yourcozy.cozy.views.mypage.profile.ProfileSetActivity
import kotlinx.android.synthetic.main.activity_myaccount.*


private lateinit var mGoogleSignInAccount: GoogleSignInAccount
private lateinit var mGoogleSignInClient: GoogleSignInClient
private lateinit var profileName: TextView
private lateinit var profileEmail: TextView
private lateinit var profileImage: ImageView
val GOOGLE_ACCOUNT: String = "google_account"
private val TAG = "ProfileActivityTAG"

private const val REQUEST_PERMISSION_CODE = 100
class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth

    private lateinit var session : Session
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
        profileEmail = findViewById(R.id.edit_detail_email)
        profileImage = findViewById(R.id.rounded_iv_account_detail_profile)

        setDataOnView()

        btn_profile_change.setOnClickListener(this)
        btn_account_detail_logout.setOnClickListener(this)
        btn_version_info.setOnClickListener(this)
        btn_privacy.setOnClickListener(this)

    }

    private fun setDataOnView(){
        Glide.with(this).load(sharedPref.getString("profile", "token")).into(profileImage)
        profileName.text = sharedPref.getString("nickname", "token")
//        profileEmail.text = mGoogleSignInAccount.email
    }
    private fun getAllImages(limit: Int? = null, offset: Int? = null) : MutableList<ItemPhoto>{
        val cursor: Cursor?
        val columnIndexData: Int
        val order = MediaStore.Video.Media.DATE_TAKEN
        val sortOrder =
            if(limit == null) "$order DESC"
            else "$order DESC LIMIT $limit OFFSET $offset"

        val photoLists : MutableList<ItemPhoto> = mutableListOf()
        val absolutePathOfImg : String? = null
        val uri: Uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )

        cursor = contentResolver.query(
            uri, projection, null, null , sortOrder
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
        when(v?.id){
            R.id.btn_account_detail_logout -> {
                Log.d(TAG, "로그아웃 버튼 클릭")
                signOut()
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
            R.id.btn_profile_change ->{
                checkPermission()
//                val intent = Intent(applicationContext, ProfileSetActivity::class.java)
//                startActivity(intent)
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
                editor.remove("token")
                editor.remove("nickname")
                editor.remove("profile")
                editor.apply()
                editor.commit()
                Log.d(TAG, "로그아웃. 성공")
                Toast.makeText(applicationContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        else{
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

    private fun checkPermission(){
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(applicationContext, ProfileSetActivity::class.java)
            startActivity(intent)
        }else{
            requestPermission()
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(applicationContext, "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(applicationContext, "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestPermission(){
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

        when(requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val intent = Intent(applicationContext, ProfileSetActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext, "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
