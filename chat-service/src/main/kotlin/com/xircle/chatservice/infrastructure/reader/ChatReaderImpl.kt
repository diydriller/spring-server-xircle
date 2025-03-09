package com.xircle.chatservice.infrastructure.reader

import com.xircle.chatservice.domain.integration.reader.ChatReader
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.infrastructure.repository.ChatRoomRepository
import com.xircle.common.exception.NotFoundException
import com.xircle.common.response.BaseResponseStatus
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class ChatReaderImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val redisTemplate: StringRedisTemplate
) : ChatReader {
    override fun findChatRoom(roomId: String): ChatRoom {
        return chatRoomRepository.findById(roomId).orElse(null)
            ?: throw NotFoundException(BaseResponseStatus.NOT_EXIST_CHAT_ROOM)
    }

    override fun findReceiverDestination(receiverId: Long): String {
        return redisTemplate.opsForValue().get("member:${receiverId}:server")!!
    }
}