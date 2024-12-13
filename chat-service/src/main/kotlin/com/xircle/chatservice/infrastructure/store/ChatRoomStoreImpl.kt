package com.xircle.chatservice.infrastructure.store

import com.xircle.chatservice.domain.integration.store.ChatRoomStore
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.infrastructure.repository.ChatRoomRepository
import org.springframework.stereotype.Component

@Component
class ChatRoomStoreImpl(
    private val chatRoomRepository: ChatRoomRepository
) : ChatRoomStore {
    override fun save(chatRoom: ChatRoom): ChatRoom {
        return chatRoomRepository.save(chatRoom)
    }
}