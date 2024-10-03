package com.xircle.core.domain.notification.dto

import com.xircle.core.domain.notification.enum.NotificationType
import java.time.LocalDateTime

data class NotificationInfo(
    var id: Long ?= null,
    val memberId : Long,
    val type: NotificationType,
    val createdAt: LocalDateTime
)
