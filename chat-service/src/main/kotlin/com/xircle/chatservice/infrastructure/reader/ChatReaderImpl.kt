package com.xircle.chatservice.infrastructure.reader

import com.xircle.chatservice.domain.integration.reader.ChatReader
import com.xircle.chatservice.domain.model.ChatMember
import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatReadStatus
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.domain.query.UnreadMessageCount
import com.xircle.chatservice.infrastructure.repository.ChatMemberRepository
import com.xircle.chatservice.infrastructure.repository.ChatReadStatusRepository
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
    private val mongoTemplate: MongoTemplate,
    private val chatReadStatusRepository: ChatReadStatusRepository,
    private val chatMemberRepository: ChatMemberRepository
) : ChatReader {
    override fun findChatRoom(roomId: String): ChatRoom {
        return chatRoomRepository.findById(roomId).orElse(null)
            ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_CHAT_ROOM)
    }

    override fun findReceiverDestination(receiverId: Long): String {
        return redisTemplate.opsForValue().get("member:${receiverId}:server")!!
    }

    override fun findAllReadStatusInEveryRoom(chatRoomIdList: List<String>, memberId: Long): List<ChatReadStatus> {
        return chatReadStatusRepository.findByRoomIdInAndMemberId(chatRoomIdList, memberId)
    }

    override fun findAllLastChatMessageInEveryRoom(chatRoomIdList: List<String>): List<ChatMessage> {
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
        val getRoomIdQuery = Query()
        getRoomIdQuery.addCriteria(Criteria.where("member_id").`is`(memberId))
        getRoomIdQuery.fields().include("room_id")
        val roomIdList = mongoTemplate.find(getRoomIdQuery, Document::class.java, "chat_member")
            .map { document -> document.getString("room_id") }

        val getPagedChatRoomQuery = Query()
        getPagedChatRoomQuery.addCriteria(Criteria.where("_id").`in`(roomIdList))
        lastMessageId?.let {
            getPagedChatRoomQuery.addCriteria(Criteria.where("last_message_id").lt(it))
        }
        getPagedChatRoomQuery.with(Sort.by(Sort.Order.desc("last_message_id"), Sort.Order.desc("created_at")))
        getPagedChatRoomQuery.limit(size)

        return mongoTemplate.find(getPagedChatRoomQuery, ChatRoom::class.java)
    }

    override fun findAllUnreadChatMessageCountInEveryRoom(
        chatReadStatusList: List<ChatReadStatus>,
        memberId: Long
    ): List<UnreadMessageCount> {
        val criteriaList = chatReadStatusList.map { chatReadStatus ->
            Criteria.where("room_id").`is`(chatReadStatus.roomId)
                .and("sender_id").ne(memberId).apply {
                    chatReadStatus.lastReadMessageId?.let {
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

    override fun findChatReadStatus(memberId: Long, roomId: String): ChatReadStatus? {
        return chatReadStatusRepository.findByRoomIdAndMemberId(roomId, memberId)
    }

    override fun findChatMember(roomId: String): List<ChatMember> {
        return chatMemberRepository.findAllByRoomId(roomId)
    }
}