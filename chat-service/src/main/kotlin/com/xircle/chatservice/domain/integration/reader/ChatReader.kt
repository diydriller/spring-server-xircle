package com.xircle.chatservice.domain.integration.reader

import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.domain.query.UnreadMessageCount

interface ChatReader {
    fun findChatRoom(roomId: String, memberId: Long): ChatRoom
    fun findReceiverDestination(receiverId: Long): String
    fun findAllLastChatMessageInEveryRoom(chatRoomList: List<ChatRoom>): List<ChatMessage>
    fun findAllChatMessage(roomId: String, lastMessageId: String?, size: Int): List<ChatMessage>
    fun findAllChatRoom(memberId: Long, lastMessageId: String?, size: Int): List<ChatRoom>
    fun findAllUnreadChatMessageCountInEveryRoom(
        chatRoomList: List<ChatRoom>,
        memberId: Long
    ): List<UnreadMessageCount>
}