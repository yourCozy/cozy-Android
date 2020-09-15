package com.example.cozy.network

import android.app.Activity
import android.content.Context
import com.example.cozy.AddCookiesInterceptor
import com.example.cozy.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RequestToServer {

    var retrofit = Retrofit.Builder()
        .baseUrl("http://52.78.69.91:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var service: RequestInterface = retrofit.create(RequestInterface::class.java)
}

class RequestToServer2(activity: Activity) {

    var client = OkHttpClient.Builder()
        .addInterceptor(AddCookiesInterceptor(activity))
        .addNetworkInterceptor(ReceivedCookiesInterceptor(activity))
        .build()

    var retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("http://52.78.69.91:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var service: RequestInterface = retrofit.create(RequestInterface::class.java)
}