package com.xircle.core.message.notification

import org.springframework.stereotype.Component

@Component
interface NotificationMessageSubscriber {
    fun subscribe(channel: String, listener: NotificationMessageListener)
    fun removeSubscribe(channel: String, listener: NotificationMessageListener)
}