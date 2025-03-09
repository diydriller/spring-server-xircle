package com.xircle.chatservice.application.service

import com.xircle.chatservice.application.dto.ChatMessageDto
import com.xircle.chatservice.application.dto.ChatRoomDto
import com.xircle.chatservice.domain.command.ChatMessageCommand
import com.xircle.chatservice.domain.command.ChatRoomCommand
import com.xircle.chatservice.domain.event.SendPrivateChatEvent
import com.xircle.chatservice.domain.integration.publisher.ChatPublisher
import com.xircle.chatservice.domain.integration.publisher.MessagePublisher
import com.xircle.chatservice.domain.integration.reader.ChatReader
import com.xircle.chatservice.domain.integration.store.ChatStore
import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.domain.model.MemberInfo
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatStore: ChatStore,
    private val chatReader: ChatReader,
    private val chatPublisher: ChatPublisher,
    private val messagePublisher: MessagePublisher
) {
    fun createRoom(chatRoomDto: ChatRoomDto): ChatRoom {
        val chatRoom = ChatRoom(name = chatRoomDto.name)
        chatRoom.addMember(
            MemberInfo(
                id = chatRoomDto.memberId,
                isHost = false
            )
        )
        chatRoom.addMember(
            MemberInfo(
                id = chatRoomDto.hostId,
                isHost = true
            )
        )
        chatStore.saveChatRoom(chatRoom)

        chatPublisher.publishCreatedChatRoom(
            ChatRoomCommand(
                sessionId = chatRoomDto.sessionId,
                hostId = chatRoomDto.hostId,
                memberId = chatRoomDto.memberId,
                roomId = chatRoom.id!!,
                roomName = chatRoom.name
            )
        )
        return chatRoom
    }

    fun chat(chatMessageDto: ChatMessageDto): ChatMessage {
        val chatRoom = chatReader.findChatRoom(chatMessageDto.roomId)
        val chatMessage = ChatMessage(
            message = chatMessageDto.message,
            roomId = chatRoom.id!!,
            senderId = chatMessageDto.senderId
        )
        chatStore.saveChatMessage(chatMessage)

        chatPublisher.publishChatMessage(
            ChatMessageCommand(
                roomId = chatMessageDto.roomId,
                sessionId = chatMessageDto.sessionId,
                senderId = chatMessageDto.senderId,
                message = chatMessageDto.message,
                receiverId = chatMessageDto.receiverId,
                chatMessageId = chatMessage.id!!
            )
        )

        val destination = chatReader.findReceiverDestination(chatMessageDto.receiverId)

        messagePublisher.publish(
            "${destination}-chat",
            SendPrivateChatEvent(
                id = chatMessage.id!!,
                roomId = chatMessageDto.roomId,
                senderId = chatMessageDto.senderId,
                message = chatMessageDto.message,
                receiverId = chatMessageDto.receiverId
            )
        )
        return chatMessage
    }
}