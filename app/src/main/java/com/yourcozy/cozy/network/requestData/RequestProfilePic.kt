package com.yourcozy.cozy.network.requestData

import android.provider.MediaStore
import okhttp3.MultipartBody

data class RequestProfilePic(
    val profile: MultipartBody.Part
)