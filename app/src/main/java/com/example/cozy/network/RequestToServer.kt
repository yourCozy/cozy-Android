package com.example.cozy.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RequestToServer {

    var interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    var client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    var retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("http://52.78.69.91:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var service: RequestInterface = retrofit.create(RequestInterface::class.java)
}