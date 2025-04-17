package com.xircle.chatservice.infrastructure.repository

import com.xircle.chatservice.domain.model.ChatRoom
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRoomRepository : MongoRepository<ChatRoom, String> {
    fun findByIdAndUserId(roomId: String, memberId: Long): ChatRoom?
}