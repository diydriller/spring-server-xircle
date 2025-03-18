package com.xircle.chatservice.application.dto

import java.time.LocalDateTime

data class GetChatMessageDto(
    var id: String? = null,
    val message: String,
    var roomId: String,
    val senderId: Long,
    val profileImage: String,
    val nickname: String,
    val createdAt: LocalDateTime
)
