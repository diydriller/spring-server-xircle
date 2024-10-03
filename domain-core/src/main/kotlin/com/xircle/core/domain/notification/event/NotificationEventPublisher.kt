package com.xircle.core.domain.notification.event

import com.xircle.core.domain.notification.dto.NotificationEventDto
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class NotificationEventPublisher(
    private val eventPublisher: ApplicationEventPublisher
) {
    fun publishEvent(event: NotificationEventDto) {
        eventPublisher.publishEvent(event)
    }
}