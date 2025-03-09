package com.xircle.chatservice.domain.event

data class SendPrivateChatEvent(
    val id: String,
    val roomId: String,
    val senderId: Long,
    val receiverId: Long,
    val message: String
)
