package com.example.shescreen.data.chat

data class ChatResponse(
    val formatted: Formatted,
    val intent: String,
    val response: String,
    val user: String
)