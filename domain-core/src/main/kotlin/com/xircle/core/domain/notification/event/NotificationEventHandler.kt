package com.xircle.core.domain.notification.event

import com.xircle.core.domain.notification.dto.NotificationEventDto
import com.xircle.core.domain.notification.service.NotificationService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class NotificationEventHandler(
    private val notificationService: NotificationService
) {
    @Async
    @EventListener
    fun handleEvent(notificationEventDto: NotificationEventDto) {
        notificationService.sendNotification(notificationEventDto)
    }
}