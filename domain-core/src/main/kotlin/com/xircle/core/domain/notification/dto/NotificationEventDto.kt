package com.xircle.core.domain.notification.dto

import com.xircle.core.domain.notification.enum.NotificationType

data class NotificationEventDto(
    val memberId : Long,
    val type: NotificationType
)