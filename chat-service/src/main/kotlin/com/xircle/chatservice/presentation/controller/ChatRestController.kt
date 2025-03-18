package com.xircle.chatservice.presentation.controller

import com.xircle.chatservice.application.service.ChatService
import com.xircle.chatservice.mapper.GetChatMessageMapper
import com.xircle.chatservice.mapper.GetChatRoomMapper
import com.xircle.chatservice.presentation.dto.GetChatMessageResponse
import com.xircle.chatservice.presentation.dto.GetChatRoomResponse
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/chat-service")
@RestController
class ChatRestController(
    private val chatService: ChatService
) {
    @GetMapping("/room")
    fun getChatRoom(
        @RequestHeader memberId: Long,
        @RequestParam lastMessageId: String?,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<BaseResponse<List<GetChatRoomResponse>>> {
        val chatRoomList = chatService.getChatRoom(memberId, lastMessageId, size)
            .map { GetChatRoomMapper.INSTANCE.covertToResponse(it) }
        return ResponseEntity.ok().body(BaseResponse(chatRoomList))
    }

    @GetMapping("/room/{roomId}/chat")
    fun getChatMessage(
        @PathVariable roomId: String,
        @RequestParam lastMessageId: String?,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<BaseResponse<List<GetChatMessageResponse>>> {
        val chatMessageList = chatService.getChatMessage(roomId, lastMessageId, size)
            .map { GetChatMessageMapper.INSTANCE.covertToResponse(it) }
        return ResponseEntity.ok().body(BaseResponse(chatMessageList))
    }

    @PutMapping("/room/{roomId}/chat/read")
    fun readChat(
        @RequestHeader memberId: Long,
        @PathVariable roomId: String,
        @RequestParam lastReadMessageId: String
    ): ResponseEntity<BaseResponse<Unit>> {
        chatService.readChat(memberId, roomId, lastReadMessageId)
        return ResponseEntity.ok().body(BaseResponse(BaseResponseStatus.SUCCESS))
    }
}