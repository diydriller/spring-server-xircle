package com.xircle.chatservice.domain.integration.publisher

import com.xircle.chatservice.domain.command.ChatMessageCommand
import com.xircle.chatservice.domain.command.ChatRoomCommand

interface ChatPublisher {
    fun publishCreatedChatRoom(chatRoomCommand: ChatRoomCommand)
    fun publishChatMessage(chatMessageCommand: ChatMessageCommand)
}