package com.xircle.chatservice.infrastructure.reader

import com.xircle.chatservice.domain.integration.reader.ChatReader
import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.domain.query.UnreadMessageCount
import com.xircle.chatservice.infrastructure.repository.ChatRoomRepository
import com.xircle.common.exception.NotFoundException
import com.xircle.common.response.BaseResponseStatus
import org.bson.Document
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class ChatReaderImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val redisTemplate: StringRedisTemplate,
    private val mongoTemplate: MongoTemplate
) : ChatReader {
    override fun findChatRoom(roomId: String, memberId: Long): ChatRoom {
        return chatRoomRepository.findByIdAndUserId(roomId, memberId)
            ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_CHAT_ROOM)
    }

    override fun findReceiverDestination(receiverId: Long): String {
        return redisTemplate.opsForValue().get("member:${receiverId}:server")!!
    }

    override fun findAllLastChatMessageInEveryRoom(chatRoomList: List<ChatRoom>): List<ChatMessage> {
        val chatRoomIdList = chatRoomList.map { chatRoom -> chatRoom.id!! }
        val matchStage = match(Criteria.where("room_id").`in`(chatRoomIdList))
        val sortStage = sort(Sort.by(Sort.Order.desc("created_at")))
        val groupStage = group("room_id")
            .first("_id").`as`("id")
            .first("message").`as`("message")
            .first("room_id").`as`("roomId")
            .first("sender_id").`as`("senderId")
        val aggregation = newAggregation(matchStage, sortStage, groupStage)
        return mongoTemplate.aggregate(aggregation, "chat_message", Document::class.java)
            .mappedResults.map { document ->
                ChatMessage(
                    id = document.getObjectId("id").toHexString(),
                    message = document.getString("message"),
                    roomId = document.getString("roomId"),
                    senderId = document.getLong("senderId")
                )
            }
    }

    override fun findAllChatMessage(roomId: String, lastMessageId: String?, size: Int): List<ChatMessage> {
        val query = Query()
        query.addCriteria(Criteria.where("room_id").`is`(roomId))
        lastMessageId?.let {
            query.addCriteria(Criteria.where("_id").gt(it))
        }
        query.with(Sort.by(Sort.Order.desc("created_at")))
        query.limit(size)
        return mongoTemplate.find(query, ChatMessage::class.java, "chat_message")
    }

    override fun findAllChatRoom(memberId: Long, lastMessageId: String?, size: Int): List<ChatRoom> {
        val query = Query()
        query.addCriteria(Criteria.where("userId").`is`(memberId))
        lastMessageId?.let {
            query.addCriteria(Criteria.where("last_message_id").lt(it))
        }
        query.with(Sort.by(Sort.Order.desc("last_message_id"), Sort.Order.desc("created_at")))
        query.limit(size)
        return mongoTemplate.find(query, ChatRoom::class.java)
    }

    override fun findAllUnreadChatMessageCountInEveryRoom(
        chatRoomList: List<ChatRoom>,
        memberId: Long
    ): List<UnreadMessageCount> {
        val criteriaList = chatRoomList.map { chatRoom ->
            Criteria.where("room_id").`is`(chatRoom.id)
                .and("sender_id").ne(memberId).apply {
                    chatRoom.lastReadMessageId?.let {
                        and("_id").gt(it)
                    }
                }
        }
        val criteria = criteriaList.takeIf { it.isNotEmpty() }
            ?.let { Criteria().orOperator(*it.toTypedArray()) }
            ?: Criteria()

        val aggregation = newAggregation(
            match(criteria),
            group("room_id").count().`as`("unreadCount")
        )

        return mongoTemplate.aggregate(
            aggregation,
            "chat_message",
            Document::class.java
        ).mappedResults.map { document ->
            UnreadMessageCount(
                roomId = document.getString("_id"),
                unreadCount = document.getInteger("unreadCount").toLong()
            )
        }
    }
}