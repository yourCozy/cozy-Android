package com.yourcozy.cozy.network

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager


object RequestToServer {

    var interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    var client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .cookieJar(JavaNetCookieJar(CookieManager()))
        .build()

    var retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("http://13.209.251.34:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var service: RequestInterface = retrofit.create(RequestInterface::class.java)
}