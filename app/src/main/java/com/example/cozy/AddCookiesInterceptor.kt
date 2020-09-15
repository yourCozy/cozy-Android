package com.example.cozy

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws


class AddCookiesInterceptor(context: Context?) : Interceptor {
    // CookieSharedReferences 객체
    private val cookieSharedPreferences: CookieSharedPreferences?

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // 가져온 chain으로 부터 빌더 객체를 생성
        val builder: Request.Builder = chain.request().newBuilder()

        // CookieSharedPreferences에 저장되어있는 쿠키 값을 가져옴

        // 빌더 헤더 영역에 쿠키 값 추가
        for (cookie in cookieSharedPreferences!!.getHashSet(
            CookieSharedPreferences.COOKIE_SHARED_PREFERENCES_KEY,
            HashSet()
        )!!) {
            builder.addHeader("Cookie", cookie)
        }

        // 체인에 빌더를 적용 및 반환
        return chain.proceed(builder.build())
    }

    /**
     * 생성자
     *
     * @param context
     */
    init {
        // CookieSharedReferences 객체 초기화
        cookieSharedPreferences = CookieSharedPreferences.getInstanceOf(context!!)
    }
}