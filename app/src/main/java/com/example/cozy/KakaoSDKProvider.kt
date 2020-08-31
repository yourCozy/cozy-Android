package com.example.cozy

import android.app.Activity
import android.app.Application
import com.kakao.auth.KakaoSDK


class KakaoSDKProvider : Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
        KakaoSDK.init(KakaoSdkAdapter())
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): KakaoSDKProvider {
        checkNotNull(instance) { "this application does not inherit com.kakao.KakaoSdkProvider" }
        return instance!!
    }

    companion object {
        var instance: KakaoSDKProvider? = null
    }
}