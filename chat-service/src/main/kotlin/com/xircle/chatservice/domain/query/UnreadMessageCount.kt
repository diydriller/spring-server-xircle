package com.xircle.chatservice.domain.query

data class UnreadMessageCount(
    val roomId: String,
    val unreadCount: Long
)