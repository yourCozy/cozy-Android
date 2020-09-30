package com.yourcozy.cozy.views.mypage.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.load.DataSource
import com.yourcozy.cozy.R
import com.yourcozy.cozy.views.mypage.MypageFragment
import kotlinx.android.synthetic.main.activity_profile_choose.*

class ProfileSetActivity : AppCompatActivity(), GalleryAdapter.ACallback {
    lateinit var mContext : Context

    private var binding: ViewDataBinding? = null
    val TAG = "PROFILE_SET_CHECK"

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

        mContext = applicationContext
        init()

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
    }
}