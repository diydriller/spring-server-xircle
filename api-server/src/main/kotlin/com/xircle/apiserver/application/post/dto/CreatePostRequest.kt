package com.xircle.apiserver.application.post.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class CreatePostRequest(
    @NotEmpty(message = "title is empty")
    val title: String,
    @NotEmpty(message = "content is empty")
    val content: String,
    val hashtagList: List<String> = ArrayList(),
    @NotNull(message = "post image is empty")
    val postImg: MultipartFile? = null
)
