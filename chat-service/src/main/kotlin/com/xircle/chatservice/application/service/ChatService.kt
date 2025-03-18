package com.xircle.chatservice.application.service

import com.xircle.chatservice.application.dto.CreateChatRoomDto
import com.xircle.chatservice.application.dto.GetChatMessageDto
import com.xircle.chatservice.application.dto.GetChatRoomDto
import com.xircle.chatservice.application.dto.SendChatMessageDto
import com.xircle.chatservice.domain.command.ChatMessageCommand
import com.xircle.chatservice.domain.command.ChatRoomCommand
import com.xircle.chatservice.domain.event.SendPrivateChatEvent
import com.xircle.chatservice.domain.integration.publisher.ChatPublisher
import com.xircle.chatservice.domain.integration.publisher.MessagePublisher
import com.xircle.chatservice.domain.integration.reader.ChatReader
import com.xircle.chatservice.domain.integration.store.ChatStore
import com.xircle.chatservice.domain.model.ChatMember
import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatReadStatus
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.infrastructure.api.client.UserServiceClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ChatService(
    private val chatStore: ChatStore,
    private val chatReader: ChatReader,
    private val chatPublisher: ChatPublisher,
    private val messagePublisher: MessagePublisher,
    private val userServiceClient: UserServiceClient
) {
    @Transactional
    fun createRoom(createChatRoomDto: CreateChatRoomDto): ChatRoom {
        val chatRoom = ChatRoom(name = createChatRoomDto.name)
        chatStore.saveChatRoom(chatRoom)

        val chatRoomMemberList = listOf(
            ChatMember(
                memberId = createChatRoomDto.memberId,
                roomId = chatRoom.id!!,
                isHost = false
            ),
            ChatMember(
                memberId = createChatRoomDto.hostId,
                roomId = chatRoom.id!!,
                isHost = true
            )
        )
        chatStore.saveChatRoomMember(chatRoomMemberList)

        chatPublisher.publishCreatedChatRoom(
            ChatRoomCommand(
                sessionId = createChatRoomDto.sessionId,
                hostId = createChatRoomDto.hostId,
                memberId = createChatRoomDto.memberId,
                roomId = chatRoom.id!!,
                roomName = chatRoom.name
            )
        )
        return chatRoom
    }

    @Transactional
    fun chat(sendChatMessageDto: SendChatMessageDto): ChatMessage {
        val chatRoom = chatReader.findChatRoom(sendChatMessageDto.roomId)
        val chatMessage = ChatMessage(
            message = sendChatMessageDto.message,
            roomId = chatRoom.id!!,
            senderId = sendChatMessageDto.senderId
        )
        chatStore.saveChatMessage(chatMessage)

        chatPublisher.publishChatMessage(
            ChatMessageCommand(
                roomId = sendChatMessageDto.roomId,
                sessionId = sendChatMessageDto.sessionId,
                senderId = sendChatMessageDto.senderId,
                message = sendChatMessageDto.message,
                receiverId = sendChatMessageDto.receiverId,
                chatMessageId = chatMessage.id!!
            )
        )

        val destination = chatReader.findReceiverDestination(sendChatMessageDto.receiverId)

        messagePublisher.publish(
            "${destination}-chat",
            SendPrivateChatEvent(
                id = chatMessage.id!!,
                roomId = sendChatMessageDto.roomId,
                senderId = sendChatMessageDto.senderId,
                message = sendChatMessageDto.message,
                receiverId = sendChatMessageDto.receiverId
            )
        )
        return chatMessage
    }
}