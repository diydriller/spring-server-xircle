package com.xircle.notificationserver.sse

import com.xircle.common.util.NumberUtil.Companion.SSE_DURATION
import com.xircle.core.domain.notification.dto.NotificationInfo
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class SseEmitterService(private val sseEmitterRepository: SseEmitterRepository) {
    fun createEmitter(emitterKey: String): SseEmitter {
        return sseEmitterRepository.save(emitterKey, SseEmitter(SSE_DURATION))
    }

    fun deleteEmitter(emitterKey: String) {
        sseEmitterRepository.deleteById(emitterKey)
    }

    fun sendNotificationToClient(emitterKey: String, notificationInfo: NotificationInfo) {
        sseEmitterRepository.findById(emitterKey)?.let{
            emitter -> send(notificationInfo, emitterKey, emitter)
        }
    }

    internal fun send(data: Any, emitterKey: String, sseEmitter: SseEmitter) {
        try {
            sseEmitter.send(
                SseEmitter.event()
                    .id(emitterKey)
                    .data(data, MediaType.APPLICATION_JSON)
            )
        } catch (e: Exception) {
            sseEmitterRepository.deleteById(emitterKey)
        }
    }
}