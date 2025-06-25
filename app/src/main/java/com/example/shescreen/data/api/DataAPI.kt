package com.example.shescreen.data.api

import com.example.shescreen.data.signup.SignUpRequest
import com.example.shescreen.data.signup.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DataAPI {
    @POST("register")
    fun signUp(
        @Body request: SignUpRequest
    ): Call<SignUpResponse>
}