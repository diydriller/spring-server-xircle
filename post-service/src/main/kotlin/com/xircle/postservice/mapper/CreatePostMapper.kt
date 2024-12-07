package com.xircle.postservice.mapper

import com.xircle.postservice.application.dto.PostDto
import com.xircle.postservice.presentation.dto.CreatePostRequest
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface CreatePostMapper {
    companion object {
        val INSTANCE: CreatePostMapper = Mappers.getMapper(CreatePostMapper::class.java)
    }

    fun covertToDto(createPostRequest: CreatePostRequest): PostDto
}