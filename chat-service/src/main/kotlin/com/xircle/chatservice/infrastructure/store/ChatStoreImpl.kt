package com.xircle.chatservice.infrastructure.store

import com.xircle.chatservice.domain.integration.store.ChatStore
import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.infrastructure.repository.ChatMessageRepository
import com.xircle.chatservice.infrastructure.repository.ChatRoomRepository
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class ChatStoreImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val mongoTemplate: MongoTemplate
) : ChatStore {
    override fun updateChatRoom(chatRoom: ChatRoom): ChatRoom {
        val query = Query(Criteria.where("_id").`is`(chatRoom.id).and("user_id").`is`(chatRoom.userId))
        val doc = mongoTemplate.converter.convertToMongoType(chatRoom) as Document

        mongoTemplate.getCollection("chat_room").replaceOne(query.queryObject, doc)
        return chatRoom
    }

    override fun saveChatRoom(chatRoom: ChatRoom): ChatRoom {
        return chatRoomRepository.save(chatRoom)
    }

    override fun saveChatMessage(chatMessage: ChatMessage): ChatMessage {
        return chatMessageRepository.save(chatMessage)
    }


}