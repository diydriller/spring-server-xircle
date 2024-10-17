package com.xircle.jpa.store.notification

import com.xircle.core.domain.notification.model.Notification
import com.xircle.core.store.notification.NotificationStore
import com.xircle.jpa.repository.notification.NotificationJpaRepository
import org.springframework.stereotype.Component

@Component
class NotificationStoreImpl(private val notificationJpaRepository: NotificationJpaRepository) : NotificationStore {
    override fun saveNotification(notification: Notification): Notification {
        return notificationJpaRepository.save(notification)
    }
}