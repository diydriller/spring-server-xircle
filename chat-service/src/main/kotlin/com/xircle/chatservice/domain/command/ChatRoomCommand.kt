package com.xircle.chatservice.domain.command

data class ChatRoomCommand(
    val sessionId: String,
    val hostId: Long,
    val memberId: Long,
    val roomId: String,
    val roomName: String,
)
