package com.yourcozy.cozy.views.mypage.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.ViewDataBinding
import com.yourcozy.cozy.R
import com.yourcozy.cozy.network.RequestToServer
import com.yourcozy.cozy.network.customEnqueue
import com.yourcozy.cozy.network.requestData.RequestProfilePic
import com.yourcozy.cozy.views.mypage.MypageFragment
import kotlinx.android.synthetic.main.activity_profile_choose.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileSetActivity : AppCompatActivity(), GalleryAdapter.ACallback, View.OnClickListener {
    lateinit var mContext : Context

    lateinit var sharedPref: SharedPreferences
    val service = RequestToServer.service

    private var binding: ViewDataBinding? = null
    val TAG = "PROFILE_SET_CHECK"

    lateinit var imgFile : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_choose)

        setSupportActionBar(tb_profile_choose)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_before)

        tb_profile_choose.elevation = 5F
        tv_profile_next.elevation = 5F

        sharedPref = this.getSharedPreferences("TOKEN", MODE_PRIVATE)

        mContext = applicationContext
        init()

        tv_profile_next.setOnClickListener(this)

    }

    private fun init(){
        val mLayoutManager : RecyclerView.LayoutManager = GridLayoutManager(mContext,3)
        rv_gallery_img.layoutManager = mLayoutManager


        Log.d(TAG,"init() 작동")
        pagedListCon()
    }

    fun pagedListCon() {
        Log.d(TAG,"pagedListCon() 작동")
        val con = PagedList.Config.Builder()
            .setInitialLoadSizeHint(30)
            .setPrefetchDistance(4)
            .setPageSize(5)
            .setEnablePlaceholders(false)
            .build()

        val builder = RxPagedListBuilder<Int, ItemPhoto>(object : androidx.paging.DataSource.Factory<Int,ItemPhoto>(){
            override fun create(): androidx.paging.DataSource<Int, ItemPhoto> {
                return GalleryDataSource(contentResolver = contentResolver)
            }
        }, con)

        val adapter = GalleryAdapter().apply {
            setCallback(this@ProfileSetActivity)
        }
        rv_gallery_img.adapter = adapter

        builder.buildObservable()
            .subscribe { adapter.submitList(it) }

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

    override fun onClickEvent(
        position: Int,
        uri: Uri
    ) {
        getGalleryItems()[position] = ItemGalleryImage(position,uri)
        imgFile = File(uri.path)
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
        when(v?.id){
            R.id.tv_profile_next ->{
                val header = mutableMapOf<String, String?>()
                header["Content-Type"] = "multipart/form-data"
                header["token"] = sharedPref.getString("token", "token")
                val rqFile = RequestBody.create(MediaType.parse("image/jpeg"), imgFile)
                var photo : MultipartBody.Part = MultipartBody.Part.createFormData("profile", imgFile.getName(), rqFile)
                service.requestProfile(RequestProfilePic(photo), header).customEnqueue(
                    onError = {

                    },
                    onSuccess = {

                    }
                )
            }
        }
    }
}