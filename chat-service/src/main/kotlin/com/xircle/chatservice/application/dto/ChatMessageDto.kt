package com.xircle.chatservice.application.dto

data class ChatMessageDto(
    val roomId: String,
    val sessionId: String,
    val senderId: Long,
    val message: String,
    val receiverId: Long
)