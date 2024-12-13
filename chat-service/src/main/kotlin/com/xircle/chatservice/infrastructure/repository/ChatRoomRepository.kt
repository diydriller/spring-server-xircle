package com.xircle.chatservice.infrastructure.repository

import com.xircle.chatservice.domain.model.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
}