package com.example.shescreen.data.api

import com.example.shescreen.data.profile.ProfileRequest
import com.example.shescreen.data.profile.ProfileResponse
import com.example.shescreen.data.signin.SignInResponse
import com.example.shescreen.data.signup.SignUpRequest
import com.example.shescreen.data.signup.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface DataAPI {
    //    @FormUrlEncoded
    @POST("register")
    fun signUp(
//        @Field("username") email: String,
//        @Field("password") password: String,
        @Body request: SignUpRequest
    ): Call<SignUpResponse>

    @PATCH("profile")
    fun profile(
        @Header("Authorization") token: String,
        @Body request: ProfileRequest
    ): Call<ProfileResponse>

    @FormUrlEncoded
    @POST("token")
    fun signIn(
        @Field("username") email: String,
        @Field("password") password: String,
    ): Call<SignInResponse>
}