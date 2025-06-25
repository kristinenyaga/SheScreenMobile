package com.example.shescreen.data.profile

data class ProfileRequest(
    val date_of_birth: String,
    val first_name: String,
    val is_parent: Boolean,
    val last_name: String,
    val phone_number: String
)