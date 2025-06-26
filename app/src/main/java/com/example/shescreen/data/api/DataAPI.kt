package com.example.shescreen.data.api

import com.example.shescreen.data.signup.SignUpRequest
import com.example.shescreen.data.signup.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DataAPI {
//    @FormUrlEncoded
    @POST("register")
    fun signUp(
//        @Field("username") email: String,
//        @Field("password") password: String,
        @Body request: SignUpRequest
    ): Call<SignUpResponse>
}