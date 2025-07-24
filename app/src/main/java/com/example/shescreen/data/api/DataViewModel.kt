package com.example.shescreen.data.api

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shescreen.data.api.RetrofitInstance.createStkPushService
import com.example.shescreen.data.api.RetrofitInstance.createTokenService
import com.example.shescreen.data.bill.BillResponse
import com.example.shescreen.data.chat.ChatResponse
import com.example.shescreen.data.daraja.StkPushRequest
import com.example.shescreen.data.daraja.StkPushResponse
import com.example.shescreen.data.daraja.TokenResponse
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthStore {
    var token: String? = null
}

class DataViewModel : ViewModel() {
    private val _prediction = MutableStateFlow<PredictionResponse?>(null)
    val prediction: StateFlow<PredictionResponse?> = _prediction.asStateFlow()

    private val _services = MutableStateFlow<ServicesResponse?>(null)
    val services: StateFlow<ServicesResponse?> = _services.asStateFlow()

    private val _botResponse = MutableStateFlow<ChatResponse?>(null)
    val botResponse: StateFlow<ChatResponse?> = _botResponse.asStateFlow()
    private val _profile = MutableStateFlow<GetProfileResponse?>(null)
    val profile: StateFlow<GetProfileResponse?> = _profile.asStateFlow()

    private val _bill = MutableStateFlow<BillResponse?>(null)
    val bill: StateFlow<BillResponse?> = _bill.asStateFlow()

    private val _recommendation = MutableStateFlow<RecommendationResponse?>(null)
    val recommendation = _recommendation.asStateFlow()

    private val _labTest = MutableStateFlow<List<LabTestResponse>?>(emptyList())
    val labTest = _labTest.asStateFlow()

    private val _followUp = MutableStateFlow<FollowUpResponse?>(null)
    val followUp = _followUp.asStateFlow()


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
                    Log.d("Risk Assessment", "Success: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Risk Assessment", "Failed: ${response.code()}, Error: $errorBody")
                }
            }

            override fun onFailure(call: Call<RiskAssessResponse>, t: Throwable) {
                Log.e("Risk Assessment", "Error: ${t.message}")
            }
        })
    }

    fun getPrediction(token: String, onResult: () -> Unit) {
        RetrofitInstance.api.getPrediction(
            token = token
        ).enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("Prediction", "Success: $body")
                    _prediction.value = body
                    onResult()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Prediction", "Failed: ${response.code()}, Error: $errorBody")
                    _prediction.value = null
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                Log.e("Prediction", "Error: ${t.message}")
                _prediction.value = null
            }
        })
    }

    fun chatBot(token: String, query: String) {
        RetrofitInstance.api.chatBot(
            token = token,
            query = query
        ).enqueue(object : Callback<ChatResponse> {
            override fun onResponse(
                call: Call<ChatResponse>,
                response: Response<ChatResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("Chat", "Success: $body")
                    _botResponse.value = body
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Chat", "Failed: ${response.code()}, Error: $errorBody")
                    _botResponse.value = null
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                Log.e("Chat", "Error: ${t.message}")
                _botResponse.value = null
            }
        })
    }

    fun getProfile(token: String, context: Context) {
        RetrofitInstance.api.getProfile(
            token = token
        ).enqueue(object : Callback<GetProfileResponse> {
            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("Chat", "Success: $body")
                    _profile.value = body
                    PrefsManager(context).savePatientId(_profile.value?.id.toString())
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Chat", "Failed: ${response.code()}, Error: $errorBody")
                    _profile.value = null
                }
            }

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                Log.e("Chat", "Error: ${t.message}")
                _profile.value = null
            }
        })
    }

    fun getServicesCost() {
        RetrofitInstance.api.getServicesCost().enqueue(object : Callback<ServicesResponse> {
            override fun onResponse(
                call: Call<ServicesResponse>,
                response: Response<ServicesResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("Services", "Success: $body")
                    _services.value = body
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Services", "Failed: ${response.code()}, Error: $errorBody")
                    _services.value = null

                }
            }

            override fun onFailure(call: Call<ServicesResponse>, t: Throwable) {
                Log.e("Services", "Error: ${t.message}")
            }
        })
    }

    fun getBill() {
        val patientId = _profile.value?.id
        if (patientId == null) {
            Log.e("Billing", "❌ Patient ID is null. Cannot fetch bill.")
            return
        }
        RetrofitInstance.api.getBill(
            patientId
        ).enqueue(object : Callback<BillResponse> {
            override fun onResponse(
                call: Call<BillResponse>,
                response: Response<BillResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("Billing", "Success: $body")
                    _bill.value = body
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Billing", "Failed: ${response.code()}, Error: $errorBody")
                }
            }

            override fun onFailure(call: Call<BillResponse>, t: Throwable) {
                Log.e("Billing", "Error: ${t.message}")
            }
        })
    }

    fun getRecommendation() {
        val patientId = _profile.value?.id
        if (patientId != null) {
            DataRepository.fetchRecommendation(patientId) { result ->
                if (result != null && result.isNotEmpty()) {
                    Log.d("Recommendation", "Success: $result")
                    _recommendation.value = result
                } else {
                    Log.e("Recommendation", "Error getting recommendation")
                    _recommendation.value = null
                }
            }
        } else {
            Log.e("Recommendation", "Patient ID not available")
        }
    }

    fun getLabTest(context: Context) {
        val patientId = _profile.value?.id
        if (patientId != null) {
            DataRepository.fetchLabTest(patientId) { result ->
                if (result != null && result.isNotEmpty()) {
                    Log.d("LabTest", "Success: $result")
                    _labTest.value = result
                    PrefsManager(context).saveFollowUpId(_labTest.value?.firstOrNull()?.follow_up_id.toString())
                } else {
                    Log.e("LabTest", "Error getting lab test")
                    _labTest.value = emptyList()
                }
            }
        } else {
            Log.e("LabTest", "Patient ID not available")
        }
    }

    fun getFollowUp() {
        val followupId = _labTest.value?.firstOrNull()?.follow_up_id
        if (followupId != null) {
            DataRepository.fetchFollowUp(followupId) { result ->
                if (result != null && result.toString().isNotEmpty()) {
                    Log.d("FollowUp", "Success: $result")
                    _followUp.value = result
                } else {
                    Log.e("FollowUp", "Error getting follow-up")
                    _followUp.value = null
                }
            }
        } else {
            Log.e("FollowUp", "FollowUp ID not available in lab tests")
        }
    }


    fun initiateStkPush(phone: String, amount: Int) {
        val tokenService = createTokenService()
        tokenService.getAccessToken().enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    val token = "Bearer ${response.body()?.accessToken}"
                    Log.d("Daraja", "✅ Access token: $token")

                    val timestamp = getTimestamp()
                    val password = getPassword(
                        shortCode = "174379",
                        passkey = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                        timestamp = timestamp
                    )

                    val stkPushRequest = StkPushRequest(
                        BusinessShortCode = "174379",
                        Password = password,
                        Timestamp = timestamp,
                        TransactionType = "CustomerPayBillOnline",
                        Amount = amount.toString(),
                        PartyA = "600584",
                        PartyB = "174379",
                        PhoneNumber = phone,
                        CallBackURL = "https://example.com/callback",
                        AccountReference = "Test123",
                        TransactionDesc = "Test Payment"
                    )

                    val stkService = createStkPushService()
                    stkService.stkPush(stkPushRequest, token)
                        .enqueue(object : Callback<StkPushResponse> {
                            override fun onResponse(
                                call: Call<StkPushResponse>,
                                response: Response<StkPushResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val body = response.body()
                                    Log.d("STK_PUSH", "✅ CustomerMessage: ${body?.CustomerMessage}")
                                } else {
                                    Log.e("STK_PUSH", "❌ Error: ${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<StkPushResponse>, t: Throwable) {
                                Log.e("STK_PUSH", "❌ Failure: ${t.message}")
                            }
                        })
                } else {
                    Log.e("Daraja", "❌ Failed to get token: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Log.e("Daraja", "Token error: ${t.message}")
            }
        })
    }
}

fun getTimestamp(): String {
    val sdf = java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault())
    return sdf.format(java.util.Date())
}

fun getPassword(shortCode: String, passkey: String, timestamp: String): String {
    val raw = shortCode + passkey + timestamp
    return Base64.encodeToString(raw.toByteArray(), Base64.NO_WRAP)
}