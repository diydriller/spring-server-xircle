package com.xircle.chatservice.infrastructure.repository

import com.xircle.chatservice.domain.model.ChatMember
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatMemberRepository : MongoRepository<ChatMember, String> {
    fun findAllByRoomId(roomId: String): List<ChatMember>
}