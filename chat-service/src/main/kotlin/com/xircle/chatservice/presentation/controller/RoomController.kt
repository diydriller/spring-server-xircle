package com.xircle.chatservice.presentation.controller

import com.xircle.chatservice.application.room.service.RoomService
import com.xircle.chatservice.presentation.dto.CreateChatRoomRequest
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class RoomController(
    private val roomService: RoomService,
    private val messagingTemplate: SimpMessagingTemplate
) {
    @MessageMapping("/room/create")
    fun createRoom(request: CreateChatRoomRequest) {
        val chatRoom = roomService.createRoom(request.hostId, request.memberId)
        messagingTemplate.convertAndSend("/topic/user/${request.hostId}", chatRoom)
    }
}