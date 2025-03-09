package com.xircle.chatservice.infrastructure.store

import com.xircle.chatservice.domain.integration.store.ChatStore
import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.infrastructure.repository.ChatMessageRepository
import com.xircle.chatservice.infrastructure.repository.ChatRoomRepository
import org.springframework.stereotype.Component

@Component
class ChatStoreImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository
) : ChatStore {
    override fun saveChatRoom(chatRoom: ChatRoom): ChatRoom {
        return chatRoomRepository.save(chatRoom)
    }

    override fun saveChatMessage(chatMessage: ChatMessage): ChatMessage {
        return chatMessageRepository.save(chatMessage)
    }
}