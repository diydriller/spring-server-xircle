package com.xircle.notificationserver.application

import com.xircle.core.domain.notification.service.NotificationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RequestMapping("/notification")
@RestController
class NotificationController(private val notificationService: NotificationService) {
    @GetMapping("/subscribe/{memberId}", produces = ["text/event-stream"])
    fun subscribe(@PathVariable memberId: String): SseEmitter {
        return notificationService.subscribe(memberId)
    }
}