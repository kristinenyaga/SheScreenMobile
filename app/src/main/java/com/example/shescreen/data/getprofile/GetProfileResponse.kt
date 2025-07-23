package com.example.shescreen.data.getprofile

data class GetProfileResponse(
    val area_of_residence: String,
    val created_at: String,
    val created_by_id: Int,
    val date_of_birth: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val patient_code: String,
    val phone_number: String
)