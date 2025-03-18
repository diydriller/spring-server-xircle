package com.xircle.chatservice.domain.integration.reader

import com.xircle.chatservice.domain.model.ChatMember
import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatReadStatus
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.domain.query.UnreadMessageCount

interface ChatReader {
    fun findChatRoom(roomId: String): ChatRoom
    fun findReceiverDestination(receiverId: Long): String
    fun findAllReadStatusInEveryRoom(chatRoomIdList: List<String>, memberId: Long): List<ChatReadStatus>
    fun findAllLastChatMessageInEveryRoom(chatRoomIdList: List<String>): List<ChatMessage>
    fun findAllChatMessage(roomId: String, lastMessageId: String?, size: Int): List<ChatMessage>
    fun findAllChatRoom(memberId: Long, lastMessageId: String?, size: Int): List<ChatRoom>
    fun findAllUnreadChatMessageCountInEveryRoom(
        chatReadStatusList: List<ChatReadStatus>,
        memberId: Long
    ): List<UnreadMessageCount>
    fun findChatReadStatus(memberId: Long, roomId: String): ChatReadStatus?
    fun findChatMember(roomId: String): List<ChatMember>
}