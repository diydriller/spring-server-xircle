package com.xircle.chatservice.application.dto

data class GetChatRoomDto(
    val roomId: String,
    val name: String,
    val unreadCount: Long,
    val lastReadMessageId: String? = null,
    val lastMessage: String? = null
)
