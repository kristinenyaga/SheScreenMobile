package com.example.shescreen.data.symptoms

data class SymptomsResponse(
    val id: Int,
    val date: String,
    val symptom: String,
    val severity: Int,
    val notes: String
)
