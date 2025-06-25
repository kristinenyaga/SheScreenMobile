package com.example.shescreen.data.profile

data class ProfileResponse(
    val date_of_birth: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val is_parent: Boolean,
    val last_name: String,
    val phone_number: String,
    val role: String
)