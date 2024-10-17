package com.xircle.core.domain.notification.event

import com.xircle.common.util.StringUtil.Companion.getChannelName
import com.xircle.core.domain.notification.dto.NotificationEventDto
import com.xircle.core.domain.notification.dto.NotificationInfo
import com.xircle.core.message.notification.NotificationMessagePublisher
import jakarta.transaction.Transactional
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class NotificationEventHandler(
    private val notificationMessagePublisher: NotificationMessagePublisher
) {
    @Async
    @Transactional
    @EventListener
    fun handleEvent(notificationEventDto: NotificationEventDto) {
        notificationMessagePublisher.publish(
            getChannelName(notificationEventDto.memberId.toString()),
            NotificationInfo(
                memberId = notificationEventDto.memberId,
                type = notificationEventDto.type
            )
        )
    }
}