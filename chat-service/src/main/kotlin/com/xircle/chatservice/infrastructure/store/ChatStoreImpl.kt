package com.xircle.chatservice.infrastructure.store

import com.xircle.chatservice.domain.integration.store.ChatStore
import com.xircle.chatservice.domain.model.ChatMember
import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatReadStatus
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.infrastructure.repository.ChatMemberRepository
import com.xircle.chatservice.infrastructure.repository.ChatMessageRepository
import com.xircle.chatservice.infrastructure.repository.ChatReadStatusRepository
import com.xircle.chatservice.infrastructure.repository.ChatRoomRepository
import org.springframework.stereotype.Component

@Component
class ChatStoreImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val chatMemberRepository: ChatMemberRepository,
    private val chatReadStatusRepository: ChatReadStatusRepository
) : ChatStore {
    override fun saveChatRoom(chatRoom: ChatRoom): ChatRoom {
        return chatRoomRepository.save(chatRoom)
    }

    override fun saveChatMessage(chatMessage: ChatMessage): ChatMessage {
        return chatMessageRepository.save(chatMessage)
    }

    override fun saveChatRoomMember(chatRoomMemberList: List<ChatMember>): List<ChatMember> {
        return chatMemberRepository.saveAll(chatRoomMemberList)
    }

    override fun saveChatReadStatus(chatReadStatus: ChatReadStatus): ChatReadStatus {
        return chatReadStatusRepository.save(chatReadStatus)
    }
}