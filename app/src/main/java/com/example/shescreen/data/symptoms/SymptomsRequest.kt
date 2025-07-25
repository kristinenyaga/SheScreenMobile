package com.example.shescreen.data.symptoms

data class SymptomsRequest(
    val symptom: String,
    val severity: Int,
    val notes: String
)
