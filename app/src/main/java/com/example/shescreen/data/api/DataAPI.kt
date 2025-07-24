package com.example.shescreen.data.api

import com.example.shescreen.data.bill.BillResponse
import com.example.shescreen.data.chat.ChatResponse
import com.example.shescreen.data.followup.FollowUpResponse
import com.example.shescreen.data.getprofile.GetProfileResponse
import com.example.shescreen.data.labtests.LabTestResponse
import com.example.shescreen.data.profile.ProfileRequest
import com.example.shescreen.data.profile.ProfileResponse
import com.example.shescreen.data.recommendation.RecommendationResponse
import com.example.shescreen.data.riskAssessment.PredictionResponse
import com.example.shescreen.data.riskAssessment.RiskAssessRequest
import com.example.shescreen.data.riskAssessment.RiskAssessResponse
import com.example.shescreen.data.services.ServicesResponse
import com.example.shescreen.data.signin.SignInResponse
import com.example.shescreen.data.signup.SignUpRequest
import com.example.shescreen.data.signup.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DataAPI {

    @POST("register")
    fun signUp(
        @Body request: SignUpRequest
    ): Call<SignUpResponse>

    @PATCH("profile")
    fun profile(
        @Header("Authorization") token: String,
        @Body request: ProfileRequest
    ): Call<ProfileResponse>

    @FormUrlEncoded
    @POST("patients/token")
    fun signIn(
        @Field("username") email: String,
        @Field("password") password: String,
    ): Call<SignInResponse>

    @POST("risk-assessment")
    fun riskAssessment(
        @Header("Authorization") token: String,
        @Body request: RiskAssessRequest
    ): Call<RiskAssessResponse>

    @GET("risk-prediction")
    fun getPrediction(
        @Header("Authorization") token: String,
    ): Call<PredictionResponse>

    @GET("patients/conversation")
    fun chatBot(
        @Header("Authorization") token: String,
        @Query("query") query: String,
    ): Call<ChatResponse>

    @GET("service-costs/")
    fun getServicesCost(
    ): Call<ServicesResponse>

    @GET("recommendations/by-patient/{id}")
    fun getRecommendation(
        @Path("id") patientId: Int
    ): Call<RecommendationResponse>

    @GET("/lab-tests/by-patient/{id}")
    fun getLabTest(
        @Path("id") patientId: Int
    ): Call<List<LabTestResponse>>

    @GET("/patients/patientfollowup/{id}")
    fun getFollowUp(
        @Path("id") patientId: Int
    ): Call<FollowUpResponse>

    @GET("billable-items/by-patient/{id}")
    fun getBill(
        @Path("id") patientId: Int
    ): Call<BillResponse>

    @GET("patients/me")
    fun getProfile(
        @Header("Authorization") token: String,
    ): Call<GetProfileResponse>
}