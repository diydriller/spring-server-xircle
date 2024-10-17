package com.xircle.core.domain.notification.dto

import com.xircle.core.domain.notification.enum.NotificationType

data class NotificationInfo(
    val memberId: Long,
    val type: NotificationType,
)
