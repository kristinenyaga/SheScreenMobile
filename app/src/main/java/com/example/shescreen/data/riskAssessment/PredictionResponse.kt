package com.example.shescreen.data.riskAssessment

data class PredictionResponse(
    val prediction: Prediction,
    val recommended_facilities: RecommendedFacilities,
    val risk_assessment: RiskAssessment,
    val summary: Summary,
    val user_id: Int,
    val user_location: UserLocation
)