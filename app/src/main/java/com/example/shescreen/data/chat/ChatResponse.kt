package com.example.shescreen.data.chat

data class ChatResponse(
    val conversation_context: Any,
    val conversation_count: Int,
    val conversation_history: Any,
    val formatted: Formatted,
    val intent: String,
    val response: String
)