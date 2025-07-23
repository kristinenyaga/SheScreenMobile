package com.example.shescreen.data.api

import android.util.Base64
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: DataAPI by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DataAPI::class.java)
    }

    fun createTokenService(): DarajaApi {
        val auth = "FjqlHQATsTWu6XBTqLp74z0wMaw1CHoXKCh7zznfJqPIW1rH:zGAC7sDjnEz6WxIBpTiclYAwmDubyrsCFiFrrw3ivIuP4UBM9xp8zlao3krYG5m9"
        val basic = "Basic " + Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", basic)
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://sandbox.safaricom.co.ke/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(DarajaApi::class.java)
    }

    fun createStkPushService(): DarajaApi {
        val client = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl("https://sandbox.safaricom.co.ke/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(DarajaApi::class.java)
    }

}
