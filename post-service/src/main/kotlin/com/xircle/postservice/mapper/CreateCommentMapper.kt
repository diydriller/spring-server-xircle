package com.xircle.postservice.mapper

import com.xircle.postservice.application.dto.CommentDto
import com.xircle.postservice.presentation.dto.CreateCommentRequest
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface CreateCommentMapper {
    companion object {
        val INSTANCE: CreateCommentMapper = Mappers.getMapper(CreateCommentMapper::class.java)
    }

    fun covertToDto(createCommentRequest: CreateCommentRequest): CommentDto
}