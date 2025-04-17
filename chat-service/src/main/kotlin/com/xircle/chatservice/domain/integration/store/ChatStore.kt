package com.xircle.chatservice.domain.integration.store

import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatRoom

interface ChatStore {
    fun updateChatRoom(chatRoom: ChatRoom): ChatRoom
    fun saveChatRoom(chatRoom: ChatRoom): ChatRoom
    fun saveChatMessage(chatMessage: ChatMessage): ChatMessage
}