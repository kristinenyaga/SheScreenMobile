package com.example.shescreen.data.riskAssessment

data class RiskAssessRequest(
    val first_sexual_intercourse_age: Int,
    val number_of_sexual_partners: Int,
    val smoking_status: String,
    val stds_history: String
)