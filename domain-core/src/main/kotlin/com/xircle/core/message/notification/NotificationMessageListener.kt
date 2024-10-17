package com.xircle.core.message.notification

import com.xircle.core.domain.notification.dto.NotificationInfo
import org.springframework.stereotype.Component

@Component
interface NotificationMessageListener {
    fun onMessage(emitterKey: String, notificationInfo: NotificationInfo)
}