package com.xircle.postservice.presentation.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class CreatePostRequest(
    @field:NotEmpty(message = "title is empty")
    val title: String = "",
    @field:NotEmpty(message = "content is empty")
    val content: String = "",
    val hashtagList: List<String> = ArrayList(),
    @field:NotNull(message = "post image is empty")
    val postImg: MultipartFile? = null
)
