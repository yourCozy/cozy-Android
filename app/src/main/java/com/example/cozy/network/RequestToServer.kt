package com.example.cozy.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestToServer {
    var retrofit = Retrofit.Builder()
        .baseUrl("http://52.78.69.91:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var service: RequestInterface = retrofit.create(RequestInterface::class.java)
}