package com.xircle.core.store.notification

import com.xircle.core.domain.notification.model.Notification
import org.springframework.stereotype.Repository

@Repository
interface NotificationStore {
    fun saveNotification(notification: Notification): Notification
}