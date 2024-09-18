package com.xircle.core.domain.post.dto

import org.springframework.web.multipart.MultipartFile

data class PostInfo (
    val title: String,
    val content: String,
    val hashtagList: List<String> = ArrayList(),
    val postImg: MultipartFile? = null,
    val memberId: Long
)