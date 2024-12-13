package com.xircle.chatservice.application.room.service

import com.xircle.chatservice.domain.integration.store.ChatRoomStore
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.domain.model.ChatRoomMember
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(
    private val chatRoomStore: ChatRoomStore
) {
    @Transactional
    fun createRoom(hostId: Long, memberId: Long): ChatRoom {
        val chatRoom = ChatRoom()
        val host = ChatRoomMember(memberId = hostId, isHost = true)
        val member = ChatRoomMember(memberId = memberId, isHost = false)
        chatRoom.addChatRoomMember(host)
        chatRoom.addChatRoomMember(member)
        chatRoomStore.save(chatRoom)
        return chatRoom
    }
}