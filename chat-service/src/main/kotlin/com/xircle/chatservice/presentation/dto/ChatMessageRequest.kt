package com.xircle.chatservice.presentation.dto

data class ChatMessageRequest(
    val roomId: String,
    val message: String,
    val receiverId: Long
)