package com.xircle.chatservice.infrastructure.repository

import com.xircle.chatservice.domain.model.ChatReadStatus
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatReadStatusRepository : MongoRepository<ChatReadStatus, String> {
    fun findByRoomIdInAndMemberId(chatRoomIdList: List<String>, memberId: Long): List<ChatReadStatus>
    fun findByRoomIdAndMemberId(roomId: String, memberId: Long): ChatReadStatus?
}