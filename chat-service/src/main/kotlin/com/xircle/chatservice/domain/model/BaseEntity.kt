package com.xircle.chatservice.domain.model

abstract class BaseEntity {
    var createdAt: Long = System.currentTimeMillis()

    var updatedAt: Long = System.currentTimeMillis()

    var isDeleted: Boolean = false
}