package com.xircle.chatservice.domain.integration.store

import com.xircle.chatservice.domain.model.ChatMember
import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatReadStatus
import com.xircle.chatservice.domain.model.ChatRoom

interface ChatStore {
    fun saveChatRoom(chatRoom: ChatRoom): ChatRoom
    fun saveChatMessage(chatMessage: ChatMessage): ChatMessage
    fun saveChatRoomMember(chatRoomMemberList: List<ChatMember>): List<ChatMember>
    fun saveChatReadStatus(chatReadStatus: ChatReadStatus): ChatReadStatus
}