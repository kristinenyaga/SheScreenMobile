package com.example.shescreen.data.recommendation

data class RecommendationResponseItem(
    val additional_services: String,
    val ai_recommendation: String,
    val created_at: String,
    val id: Int,
    val is_override: Boolean,
    val non_test_recommendations: String,
    val notes: String,
    val patient: Patient,
    val patient_id: Int,
    val referral: Any,
    val risk_prediction_id: Int,
    val status: String,
    val test_recommendations: String,
    val urgency: String
)