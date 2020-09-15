package com.example.cozy

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.util.*


/**
 * CookieSharedPreferences 클래스
 *
 * @author devetude
 */
class CookieSharedPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences

    /**
     * SharedPreferences에 값을 추가하는 메소드
     *
     * @param key
     * @param hashSet
     */
    fun putHashSet(key: String?, hashSet: HashSet<String?>?) {
        val editor = sharedPreferences.edit()
        editor.putStringSet(key, hashSet)
        editor.commit()
    }

    /**
     * SharedPreferences에서 값을 가져오는 메소드
     *
     * @param key
     * @param cookie
     * @return
     */
    fun getHashSet(
        key: String?,
        cookie: HashSet<String>?
    ): HashSet<String>? {
        return try {
            sharedPreferences.getStringSet(key, cookie) as HashSet<String>?
        } catch (e: Exception) {
            cookie
        }
    }

    companion object {
        /**
         * CookieSharedPreferences를 참조하기 위한 key
         *
         * 권고) 겹치지 않는 고유한 형태의 string으로 구성할 것
         */
        const val COOKIE_SHARED_PREFERENCES_KEY = "new.devetude.www.cookie"

        // 싱글톤 모델로 객체 초기화
        private var cookieSharedPreferences: CookieSharedPreferences? = null
        fun getInstanceOf(c: Context): CookieSharedPreferences? {
            if (cookieSharedPreferences == null) {
                cookieSharedPreferences =
                    CookieSharedPreferences(c)
            }
            return cookieSharedPreferences
        }
    }

    /**
     * 생성자
     *
     * @param context
     */
    init {
        val COOKIE_SHARED_PREFERENCE_NAME = context.packageName
        sharedPreferences =
            context.getSharedPreferences(COOKIE_SHARED_PREFERENCE_NAME, Activity.MODE_PRIVATE)
    }
}