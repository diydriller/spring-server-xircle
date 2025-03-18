package com.xircle.chatservice.presentation.dto

import java.time.LocalDateTime

data class GetChatMessageResponse(
    var id: String? = null,
    val message: String,
    var roomId: String,
    val senderId: Long,
    val profileImage: String,
    val nickname: String,
    val createdAt: LocalDateTime
)