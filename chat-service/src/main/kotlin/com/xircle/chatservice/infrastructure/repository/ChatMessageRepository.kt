package com.xircle.chatservice.infrastructure.repository

import com.xircle.chatservice.domain.model.ChatMessage
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatMessageRepository : MongoRepository<ChatMessage, String> {
}