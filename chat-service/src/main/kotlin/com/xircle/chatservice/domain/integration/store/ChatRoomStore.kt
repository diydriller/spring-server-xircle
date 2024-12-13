package com.xircle.chatservice.domain.integration.store

import com.xircle.chatservice.domain.model.ChatRoom

interface ChatRoomStore {
    fun save(chatRoom: ChatRoom): ChatRoom
}