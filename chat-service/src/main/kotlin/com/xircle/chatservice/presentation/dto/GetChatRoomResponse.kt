package com.xircle.chatservice.presentation.dto

data class GetChatRoomResponse(
    val roomId: String,
    val name: String,
    val unreadCount: Int,
    val lastReadMessageId: String? = null,
    val lastMessage: String? = null
)