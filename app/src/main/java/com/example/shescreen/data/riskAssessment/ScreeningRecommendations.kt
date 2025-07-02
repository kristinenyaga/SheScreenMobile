package com.example.shescreen.data.riskAssessment

data class ScreeningRecommendations(
    val additional_services: List<String>,
    val frequency: String,
    val reason: String,
    val recommended_screenings: List<String>,
    val urgency: String
)