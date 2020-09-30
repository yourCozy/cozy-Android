package com.yourcozy.cozy.views.mypage.profile

import android.net.Uri

data class ItemGalleryImage (
    var position: Int,
    var uri: Uri? = null
)

private val result: MutableList<ItemGalleryImage> = mutableListOf()

fun Int.initGalleryItems() : MutableList<ItemGalleryImage> {
    for (i in 0 until this) {
        result.add(ItemGalleryImage(i, null))
    }

    return result
}

fun getGalleryItems() = result