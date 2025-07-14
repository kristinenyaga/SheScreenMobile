package com.example.shescreen.data.cms

import kotlinx.serialization.Serializable

@Serializable
data class Category (
    val id: Int,
    val title: String,
)