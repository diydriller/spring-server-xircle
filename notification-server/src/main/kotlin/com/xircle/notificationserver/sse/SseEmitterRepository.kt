package com.xircle.notificationserver.sse

import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Repository
class SseEmitterRepository {
    private val emitters: MutableMap<String, SseEmitter> = ConcurrentHashMap()

    fun save(eventId: String, sseEmitter: SseEmitter): SseEmitter {
        emitters[eventId] = sseEmitter
        return sseEmitter
    }

    fun findById(memberId: String): SseEmitter? {
        return emitters[memberId]
    }

    fun deleteById(eventId: String) {
        emitters.remove(eventId)
    }
}