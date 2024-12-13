package com.xircle.chatservice.presentation.dto

data class CreateChatRoomRequest(
    val hostId: Long,
    val memberId: Long
)
