package com.yourcozy.cozy.views.mypage.profile

import android.content.ContentResolver
import android.provider.MediaStore
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource

class GalleryDataSourceFactory(private val contentResolver: ContentResolver) : DataSource.Factory<Int, ItemPhoto>() {
    override fun create(): DataSource<Int, ItemPhoto> {
        return GalleryDataSource(contentResolver)
    }
}

class GalleryDataSource(private val contentResolver: ContentResolver) : PositionalDataSource<ItemPhoto>(){
    private val TAG: String = GalleryDataSource::class.java.simpleName

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<ItemPhoto>) {
        Log.i(TAG, "loadInitial start: ${params.requestedStartPosition}, size: ${params.requestedLoadSize}")

        callback.onResult(getImages(params.requestedStartPosition, params.requestedLoadSize), 0)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ItemPhoto>) {
        callback.onResult(getImages(params.startPosition, params.loadSize))
    }

    private fun getImages(startPosition: Int, limit: Int): MutableList<ItemPhoto> {//limit = loadSize
        val photos: MutableList<ItemPhoto> = mutableListOf()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
        )

        val orderBy = MediaStore.Video.Media.DATE_TAKEN // 최신순
        val sortOrder = "$orderBy DESC LIMIT $limit OFFSET $startPosition"//offset = startPostion
        val imgCursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection, null, null, sortOrder
        )

        if (imgCursor != null) {
            if (imgCursor.moveToFirst()) {
                do{
                    val imgData = imgCursor.getColumnIndex(MediaStore.Images.Media.DATA)
                    val imgDataPath = imgCursor.getString(imgData)
                    photos.add(ItemPhoto(imgDataPath))
                    imgCursor.moveToNext()
                } while (imgCursor.moveToNext())
            }
            imgCursor.close()
        }

        photos.size.initGalleryItems()

        return photos
    }
}