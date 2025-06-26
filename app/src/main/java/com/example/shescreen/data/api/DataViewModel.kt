package com.example.shescreen.data.api

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shescreen.data.profile.ProfileRequest
import com.example.shescreen.data.profile.ProfileResponse
import com.example.shescreen.data.signin.SignInResponse
import com.example.shescreen.data.signup.SignUpRequest
import com.example.shescreen.data.signup.SignUpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthStore {
    var token: String? = null
}
class DataViewModel : ViewModel() {
    private val _signInResponse = MutableStateFlow<SignInResponse?>(null)
    val signInResponse: StateFlow<SignInResponse?> = _signInResponse

    fun signUp(email: String, password: String) {
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
    fun signIn(email: String, password: String, onSuccess: (String) -> Unit) {
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
        token: String
    ) {
//        val token = AuthStore.token
//        if (token == null) {
//            Log.e("Profile", "Access token is missing!")
//            return
//        }
        val body = ProfileRequest(
            date_of_birth = dateOfBirth,
            first_name = firstName,
            last_name = lastName,
            phone_number = phoneNumber,
            is_parent = isParent
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


}