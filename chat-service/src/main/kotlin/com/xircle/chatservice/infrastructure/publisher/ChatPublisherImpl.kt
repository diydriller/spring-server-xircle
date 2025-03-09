package com.xircle.chatservice.infrastructure.publisher

import com.xircle.chatservice.domain.command.ChatMessageCommand
import com.xircle.chatservice.domain.command.ChatRoomCommand
import com.xircle.chatservice.domain.event.CreatePrivateChatRoomEvent
import com.xircle.chatservice.domain.event.SendPrivateChatEvent
import com.xircle.chatservice.domain.integration.publisher.ChatPublisher
import com.xircle.chatservice.util.SocketUtil.createHeaders
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class ChatPublisherImpl(
    private val messagingTemplate: SimpMessagingTemplate
) : ChatPublisher {
    override fun publishCreatedChatRoom(chatRoomCommand: ChatRoomCommand) {
        messagingTemplate.convertAndSendToUser(
            chatRoomCommand.sessionId, "/queue/room",
            CreatePrivateChatRoomEvent(
                id = chatRoomCommand.roomId,
                name = chatRoomCommand.roomName,
                hostId = chatRoomCommand.hostId,
                memberId = chatRoomCommand.memberId
            ),
            createHeaders(chatRoomCommand.sessionId)
        )
    }

    override fun publishChatMessage(chatMessageCommand: ChatMessageCommand) {
        messagingTemplate.convertAndSendToUser(
            chatMessageCommand.sessionId, "/queue/chat",
            SendPrivateChatEvent(
                id = chatMessageCommand.chatMessageId,
                roomId = chatMessageCommand.roomId,
                senderId = chatMessageCommand.senderId,
                message = chatMessageCommand.message,
                receiverId = chatMessageCommand.receiverId
            ), createHeaders(chatMessageCommand.sessionId)
        )
    }
}