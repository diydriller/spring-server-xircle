package com.xircle.chatservice.application.dto

data class ChatRoomDto(
    val name: String,
    val sessionId: String,
    val hostId: Long,
    val memberId: Long
)
