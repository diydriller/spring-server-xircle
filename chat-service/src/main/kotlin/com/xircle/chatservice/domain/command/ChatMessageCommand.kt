package com.xircle.chatservice.domain.command

data class ChatMessageCommand(
    val roomId: String,
    val sessionId: String,
    val senderId: Long,
    val message: String,
    val receiverId: Long,
    val chatMessageId: String
)
