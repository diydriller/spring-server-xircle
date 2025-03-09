package com.xircle.chatservice.domain.integration.reader

import com.xircle.chatservice.domain.model.ChatRoom

interface ChatReader {
    fun findChatRoom(roomId: String): ChatRoom
    fun findReceiverDestination(receiverId: Long): String
}