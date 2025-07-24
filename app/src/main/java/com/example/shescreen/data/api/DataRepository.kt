package com.example.shescreen.data.api

import android.util.Log
import com.example.shescreen.data.followup.FollowUpResponse
import com.example.shescreen.data.labtests.LabTestResponse
import com.example.shescreen.data.recommendation.RecommendationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataRepository {
    fun fetchRecommendation(patientId: Int, callback: (RecommendationResponse?) -> Unit) {
        RetrofitInstance.api.getRecommendation(
            patientId
        ).enqueue(object : Callback<RecommendationResponse> {
            override fun onResponse(
                call: Call<RecommendationResponse>,
                response: Response<RecommendationResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                    Log.d("DataRepository", "Recommendation response: ${response.body()}")
                } else callback(null)
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                Log.e("DataRepository", "Failed to fetch recommendation", t)
                callback(null)
            }
        })
    }

    fun fetchLabTest(patientId: Int, callback: (List<LabTestResponse>?) -> Unit) {
        RetrofitInstance.api.getLabTest(
            patientId
        ).enqueue(object : Callback<List<LabTestResponse>> {
            override fun onResponse(
                call: Call<List<LabTestResponse>>,
                response: Response<List<LabTestResponse>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                    Log.d("DataRepository", "Lab test response: ${response.body()}")
                } else callback(null)
            }

            override fun onFailure(call: Call<List<LabTestResponse>>, t: Throwable) {
                Log.e("DataRepository", "Failed to fetch lab test", t)
                callback(null)
            }
        })
    }

    fun fetchFollowUp(patientId: Int, callback: (FollowUpResponse?) -> Unit) {
        RetrofitInstance.api.getFollowUp(
            patientId
        ).enqueue(object : Callback<FollowUpResponse> {
            override fun onResponse(
                call: Call<FollowUpResponse>,
                response: Response<FollowUpResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                    Log.d("DataRepository", "Follow uo response: ${response.body()}")
                } else callback(null)
            }

            override fun onFailure(call: Call<FollowUpResponse>, t: Throwable) {
                Log.e("DataRepository", "Failed to fetch follow up", t)
                callback(null)
            }
        })
    }
}
