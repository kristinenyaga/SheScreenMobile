package com.example.shescreen.data.cms

import kotlinx.serialization.Serializable

@Serializable
data class EducationalContent(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val category_id: Int,
)
