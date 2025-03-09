package com.xircle.chatservice.presentation.controller

import com.xircle.chatservice.application.service.ChatService
import com.xircle.chatservice.mapper.ChatMessageMapper
import com.xircle.chatservice.mapper.CreateChatRoomMapper
import com.xircle.chatservice.presentation.dto.ChatMessageRequest
import com.xircle.chatservice.presentation.dto.CreateChatRoomRequest
import com.xircle.chatservice.session.SessionManager
import com.xircle.common.exception.BaseException
import com.xircle.common.response.BaseResponseStatus
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val chatService: ChatService,
    private val sessionManager: SessionManager
) {
    @MessageMapping("/room/create")
    fun createRoom(
        @Header("simpSessionId") sessionId: String,
        @Payload request: CreateChatRoomRequest
    ) {
        val memberId = sessionManager.getUser(sessionId)
            ?: throw BaseException(BaseResponseStatus.NOT_AUTHENTICATION_ERROR)

        val createChatRoomDto = CreateChatRoomMapper.INSTANCE.covertToDto(request, sessionId, memberId.toLong())

        chatService.createRoom(createChatRoomDto)
    }


    @MessageMapping("/chat")
    fun chat(
        @Header("simpSessionId") sessionId: String,
        @Payload request: ChatMessageRequest
    ) {
        val memberId = sessionManager.getUser(sessionId)
            ?: throw BaseException(BaseResponseStatus.NOT_AUTHENTICATION_ERROR)

        val chatMessageDto = ChatMessageMapper.INSTANCE.covertToDto(request, sessionId, memberId.toLong())

        chatService.chat(chatMessageDto)
    }
}