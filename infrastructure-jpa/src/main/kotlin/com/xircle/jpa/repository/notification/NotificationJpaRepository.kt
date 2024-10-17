package com.xircle.jpa.repository.notification

import com.xircle.core.domain.notification.model.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationJpaRepository : JpaRepository<Notification, Long> {
    fun save(notification: Notification): Notification
}