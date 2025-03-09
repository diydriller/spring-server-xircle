package com.xircle.chatservice.mapper

import com.xircle.chatservice.application.dto.ChatMessageDto
import com.xircle.chatservice.presentation.dto.ChatMessageRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface ChatMessageMapper {
    companion object {
        val INSTANCE: ChatMessageMapper = Mappers.getMapper(ChatMessageMapper::class.java)
    }

    @Mapping(source = "sessionId", target = "sessionId")
    @Mapping(source = "senderId", target = "senderId")
    fun covertToDto(
        chatMessageRequest: ChatMessageRequest,
        sessionId: String,
        senderId: Long
    ): ChatMessageDto
}