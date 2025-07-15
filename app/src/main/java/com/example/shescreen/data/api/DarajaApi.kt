package com.example.shescreen.data.api

import com.example.shescreen.data.daraja.StkPushRequest
import com.example.shescreen.data.daraja.StkPushResponse
import com.example.shescreen.data.daraja.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface DarajaApi {
    @GET("oauth/v1/generate")
    fun getAccessToken(
        @Query("grant_type") grantType: String = "client_credentials"
    ): Call<TokenResponse>

    @POST("mpesa/stkpush/v1/processrequest")
    fun stkPush(
        @Body request: StkPushRequest,
        @Header("Authorization") token: String
    ): Call<StkPushResponse>
}