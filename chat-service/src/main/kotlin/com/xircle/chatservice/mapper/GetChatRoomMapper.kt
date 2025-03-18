package com.xircle.chatservice.mapper

import com.xircle.chatservice.application.dto.GetChatRoomDto
import com.xircle.chatservice.presentation.dto.GetChatRoomResponse
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface GetChatRoomMapper {
    companion object {
        val INSTANCE: GetChatRoomMapper = Mappers.getMapper(GetChatRoomMapper::class.java)
    }

    fun covertToResponse(
        getChatRoomDto: GetChatRoomDto
    ): GetChatRoomResponse
}