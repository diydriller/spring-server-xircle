package com.xircle.notificationserver.sse

import com.xircle.core.domain.notification.dto.NotificationInfo
import com.xircle.core.domain.notification.model.Notification
import com.xircle.core.message.notification.NotificationMessageListener
import com.xircle.core.store.notification.NotificationStore
import org.springframework.stereotype.Component

@Component
class NotificationMessageListenerImpl(
    private val sseEmitterService: SseEmitterService,
    private val notificationStore: NotificationStore
) : NotificationMessageListener {
    override fun onMessage(emitterKey: String, notificationInfo: NotificationInfo) {
        val notification = Notification(memberId = notificationInfo.memberId, type = notificationInfo.type)
        notificationStore.saveNotification(notification)
        sseEmitterService.sendNotificationToClient(emitterKey, notificationInfo)
    }
}