package com.xircle.chatservice.infrastructure.consumer

import com.xircle.chatservice.domain.command.ChatMessageCommand
import com.xircle.chatservice.domain.event.SendPrivateChatEvent
import com.xircle.chatservice.domain.integration.publisher.ChatPublisher
import com.xircle.chatservice.session.SessionManager
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ChatConsumer(
    private val sessionManager: SessionManager,
    private val chatPublisher: ChatPublisher
) {
    @KafkaListener(topics = ["\${server.instance}.\${server.port}-chat"], groupId = "chat-group")
    fun listenRoom(message: SendPrivateChatEvent) {
        val sessionId = sessionManager.getSession(message.receiverId.toString())
        chatPublisher.publishChatMessage(
            ChatMessageCommand(
                roomId = message.roomId,
                sessionId = sessionId!!,
                senderId = message.senderId,
                message = message.message,
                receiverId = message.receiverId,
                chatMessageId = message.id
            )
        )
    }
}
