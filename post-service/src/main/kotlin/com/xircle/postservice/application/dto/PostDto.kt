package com.xircle.postservice.application.dto

import org.springframework.web.multipart.MultipartFile

data class PostDto (
    val title: String,
    val content: String,
    val hashtagList: List<String> = ArrayList(),
    val postImg: MultipartFile? = null,
    val memberId: Long
)