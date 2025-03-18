package com.xircle.chatservice.domain.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

abstract class BaseEntity {
    @CreatedDate
    @Field("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Field("updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Field("is_deleted")
    var isDeleted: Boolean = false
}