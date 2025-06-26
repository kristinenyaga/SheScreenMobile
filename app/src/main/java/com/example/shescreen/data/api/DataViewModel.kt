package com.example.shescreen.data.api

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shescreen.data.signup.SignUpRequest
import com.example.shescreen.data.signup.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel : ViewModel() {
    fun signUp(email: String, password: String) {
        val body = SignUpRequest(
            email = email,
            password = password,
        )
        RetrofitInstance.api.signUp(
//           email = email,
//            password = password
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
}