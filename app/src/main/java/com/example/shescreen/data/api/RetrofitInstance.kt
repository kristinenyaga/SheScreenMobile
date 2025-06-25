package com.example.shescreen.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: DataAPI by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.100.77:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DataAPI::class.java)
    }
}