package com.xircle.chatservice.session

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class SessionManager {
    private val sessionToUser = ConcurrentHashMap<String, String>()

    private val userToSession = ConcurrentHashMap<String, String>()

    fun add(sessionId: String, memberId: String) {
        sessionToUser[sessionId] = memberId
        userToSession[memberId] = sessionId
    }

    fun remove(sessionId: String, memberId: String) {
        sessionToUser.remove(sessionId)
        userToSession.remove(memberId)
    }

    fun getUser(sessionId: String): String? {
        return sessionToUser[sessionId]
    }

    fun getSession(memberId: String): String? {
        return userToSession[memberId]
    }
}