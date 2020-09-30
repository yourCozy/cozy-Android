package com.yourcozy.cozy.views.mypage.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val activity = application.applicationContext

    fun getImages() : LiveData<PagedList<ItemPhoto>> {
        val dataSourceFactory = GalleryDataSourceFactory(activity.contentResolver)
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(60)
            .setEnablePlaceholders(false)
            .build()

        val data = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
        return data
    }
}