package com.xircle.chatservice.presentation.dto

data class CreateChatRoomRequest(
    val name: String,
    val memberId: Long
)
