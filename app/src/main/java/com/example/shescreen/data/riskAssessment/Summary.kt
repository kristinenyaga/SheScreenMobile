package com.example.shescreen.data.riskAssessment

data class Summary(
    val additional_services: List<String>,
    val next_steps: List<String>,
    val reason: String,
    val risk_level: String
)