package com.xircle.chatservice.mapper

import com.xircle.chatservice.application.dto.CreateChatRoomDto
import com.xircle.chatservice.presentation.dto.CreateChatRoomRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface CreateChatRoomMapper {
    companion object {
        val INSTANCE: CreateChatRoomMapper = Mappers.getMapper(CreateChatRoomMapper::class.java)
    }

    @Mapping(source = "sessionId", target = "sessionId")
    @Mapping(source = "hostId", target = "hostId")
    fun covertToDto(
            createChatRoomRequest: CreateChatRoomRequest,
            sessionId: String,
            hostId: Long
        ): CreateChatRoomDto
}