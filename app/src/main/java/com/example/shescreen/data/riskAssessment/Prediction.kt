package com.example.shescreen.data.riskAssessment

data class Prediction(
    val cluster: Int,
    val interpretation: String,
    val screening_recommendations: ScreeningRecommendations
)