package com.xircle.chatservice.mapper

import com.xircle.chatservice.application.dto.GetChatMessageDto
import com.xircle.chatservice.presentation.dto.GetChatMessageResponse
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface GetChatMessageMapper {
    companion object {
        val INSTANCE: GetChatMessageMapper = Mappers.getMapper(GetChatMessageMapper::class.java)
    }

    fun covertToResponse(
        getChatMessageDto: GetChatMessageDto
    ): GetChatMessageResponse
}