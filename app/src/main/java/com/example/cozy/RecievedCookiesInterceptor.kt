package com.example.cozy

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws


class ReceivedCookiesInterceptor(context: Context?) :
    Interceptor {
    // CookieSharedReferences 객체
    private val cookieSharedPreferences: CookieSharedPreferences?

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // 가져온 chain으로 부터 리스폰스 객체를 생성
        val response = chain.proceed(chain.request())

        // 리스폰스의 헤더 영역에 Set-Cookie가 설정되어있는 경우
        if (!response.headers("Set-Cookie").isEmpty()) {
            val cookies: HashSet<String?> = HashSet()

            // 쿠키 값을 읽어옴
            for (header in response.headers("Set-Cookie")) {
                cookies.add(header)
            }

            // 쿠키 값을 CookieSharedPreferences에 저장
            cookieSharedPreferences!!.putHashSet(
                CookieSharedPreferences.COOKIE_SHARED_PREFERENCES_KEY,
                cookies
            )
        }

        // 리스폰스 객체 반환
        return response
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