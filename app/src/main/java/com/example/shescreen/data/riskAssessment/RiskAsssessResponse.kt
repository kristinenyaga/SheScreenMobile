package com.example.shescreen.data.riskAssessment

data class RiskAssessResponse(
    val additional_services: String,
    val age_at_assessment: Int,
    val cluster: Int,
    val created_at: String,
    val first_sexual_intercourse_age: Int,
    val frequency: String,
    val id: Int,
    val interpretation: String,
    val number_of_sexual_partners: Int,
    val reason: String,
    val recommended_screenings: String,
    val risk_level: String,
    val smoking_status: String,
    val stds_history: String,
    val urgency: String,
    val user_id: Int
)