package com.xircle.notificationserver.application

import com.xircle.common.util.StringUtil.Companion.getChannelName
import com.xircle.core.message.notification.NotificationMessageListener
import com.xircle.core.message.notification.NotificationMessageSubscriber
import com.xircle.notificationserver.sse.SseEmitterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RequestMapping("/notification")
@RestController
class NotificationController(
    private val sseEmitterService: SseEmitterService,
    private val notificationMessageSubscriber: NotificationMessageSubscriber,
    private val notificationMessageListener: NotificationMessageListener
) {
    @GetMapping("/subscribe/{memberId}", produces = ["text/event-stream"])
    fun subscribe(@PathVariable memberId: String): SseEmitter {
        val sseEmitter = sseEmitterService.createEmitter(memberId)
        sseEmitterService.send("subscribe", memberId, sseEmitter)

        notificationMessageSubscriber.subscribe(getChannelName(memberId), notificationMessageListener)

        sseEmitter.onTimeout { sseEmitter.complete() }
        sseEmitter.onError { sseEmitter.complete() }
        sseEmitter.onCompletion {
            sseEmitterService.deleteEmitter(memberId)
            notificationMessageSubscriber.removeSubscribe(getChannelName(memberId), notificationMessageListener)
        }
        return sseEmitter
    }
}