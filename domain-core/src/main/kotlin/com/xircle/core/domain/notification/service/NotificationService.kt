package com.xircle.core.domain.notification.service

import com.xircle.common.util.StringUtil.Companion.getChannelName
import com.xircle.core.domain.notification.dto.NotificationEventDto
import com.xircle.core.domain.notification.dto.NotificationInfo
import com.xircle.core.domain.notification.model.Notification
import com.xircle.core.repository.notification.NotificationJpaRepository
import com.xircle.core.repository.notification.RedisMessageService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class NotificationService(
    private val notificationRepository: NotificationJpaRepository,
    private val sseEmitterService: SseEmitterService,
    private val redisMessageService: RedisMessageService
) {
    fun subscribe(memberId: String): SseEmitter {
        val sseEmitter = sseEmitterService.createEmitter(memberId)
        sseEmitterService.send("subscribe", memberId, sseEmitter)

        redisMessageService.subscribe(getChannelName(memberId))

        sseEmitter.onTimeout { sseEmitter.complete() }
        sseEmitter.onError { sseEmitter.complete() }
        sseEmitter.onCompletion {
            sseEmitterService.deleteEmitter(memberId)
            redisMessageService.removeSubscribe(memberId)
        }
        return sseEmitter
    }

    @Transactional
    fun sendNotification(notificationEventDto: NotificationEventDto) {
        val notification = Notification(memberId = notificationEventDto.memberId, type = notificationEventDto.type)
        val savedNotification = notificationRepository.save(notification)
        redisMessageService.publish(
            getChannelName(notificationEventDto.memberId.toString()),
            NotificationInfo(
                id = savedNotification.id,
                memberId = notificationEventDto.memberId,
                type = notificationEventDto.type,
                createdAt = savedNotification.createdAt!!
            )
        )
    }
}