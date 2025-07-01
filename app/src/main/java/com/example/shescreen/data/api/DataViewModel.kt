package com.example.shescreen.data.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shescreen.data.profile.ProfileRequest
import com.example.shescreen.data.profile.ProfileResponse
import com.example.shescreen.data.riskAssessment.RiskAssessRequest
import com.example.shescreen.data.riskAssessment.RiskAssessResponse
import com.example.shescreen.data.signin.SignInResponse
import com.example.shescreen.data.signup.SignUpRequest
import com.example.shescreen.data.signup.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthStore {
    var token: String? = null
}

class DataViewModel : ViewModel() {

    fun signUp(email: String, password: String, context: Context) {
        val body = SignUpRequest(
            email = email,
            password = password,
        )
        RetrofitInstance.api.signUp(
            request = body
        ).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("SignUp", "Success: ${response.body()}")
                    PrefsManager(context).clear()
                    PrefsManager(context).saveUserDetails(email, password)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("SignUp", "Failed: ${response.code()}, Error: $errorBody")
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.e("SignUp", "Error: ${t.message}")
            }
        })
    }

    fun signIn(email: String, password: String, onSuccess: (String) -> Unit, context: Context) {
        RetrofitInstance.api.signIn(
            email = email,
            password = password
        ).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                if (response.isSuccessful) {
                    val accessToken = response.body()?.access_token
                    AuthStore.token = accessToken
                    Log.d("SignIn", "Success: ${response.body()}")
                    accessToken?.let { onSuccess(it) }
                    PrefsManager(context).saveAuthToken(accessToken.toString())
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("SignIn", "Failed: ${response.code()}, Error: $errorBody")
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Log.e("SignIn", "Error: ${t.message}")
            }
        })
    }

    fun profile(
        dateOfBirth: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        isParent: Boolean,
        region: String,
        token: String
    ) {
        val body = ProfileRequest(
            date_of_birth = dateOfBirth,
            first_name = firstName,
            last_name = lastName,
            phone_number = phoneNumber,
            is_parent = isParent,
            region = region
        )
        RetrofitInstance.api.profile(
            request = body,
            token = token
        ).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("Profile", "Success: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Profile", "Failed: ${response.code()}, Error: $errorBody")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e("Profile", "Error: ${t.message}")
            }
        })
    }

    fun riskAssessment(
        sexualPartners: Int,
        firstSexualIntercourseAge: Int,
        smokingStatus: String,
        stdHistory: String,
        token: String
    ) {
        val body = RiskAssessRequest(
            first_sexual_intercourse_age = firstSexualIntercourseAge,
            number_of_sexual_partners = sexualPartners,
            smoking_status = smokingStatus,
            stds_history = stdHistory
        )
        RetrofitInstance.api.riskAssessment(
            request = body,
            token = token
        ).enqueue(object : Callback<RiskAssessResponse> {
            override fun onResponse(
                call: Call<RiskAssessResponse>,
                response: Response<RiskAssessResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("SignUp", "Success: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("SignUp", "Failed: ${response.code()}, Error: $errorBody")
                }
            }

            override fun onFailure(call: Call<RiskAssessResponse>, t: Throwable) {
                Log.e("SignUp", "Error: ${t.message}")
            }
        })
    }


}