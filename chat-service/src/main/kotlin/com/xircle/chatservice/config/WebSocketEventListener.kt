package com.xircle.chatservice.config

import com.xircle.chatservice.auth.TokenService
import com.xircle.chatservice.infrastructure.api.client.UserServiceClient
import com.xircle.chatservice.session.SessionManager
import com.xircle.common.dto.MemberInfo
import com.xircle.common.exception.BaseException
import com.xircle.common.response.BaseResponseStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import java.net.InetAddress

@Component
class WebSocketEventListener(
    private val tokenService: TokenService,
    private val sessionManager: SessionManager,
    private val userServiceClient: UserServiceClient,
    private val redisTemplate: StringRedisTemplate
) {
    @Value("\${server.port}")
    private lateinit var serverPort: String

    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectEvent) {
        val accessor = StompHeaderAccessor.wrap(event.message)
        val token = accessor.getFirstNativeHeader("token") ?: return

        val memberInfo: MemberInfo?
        try {
            tokenService.verifyToken(token)
            val decodedToken = tokenService.decodeToken(token)
            val memberId = decodedToken.getClaim("id").asLong()

            memberInfo = userServiceClient.getMemberInfo(memberId)
        } catch (_: Exception) {
            throw BaseException(BaseResponseStatus.NOT_AUTHENTICATION_ERROR)
        }

        val sessionId = accessor.sessionId!!
        sessionManager.add(sessionId, memberInfo!!.id.toString())

        val serverId = InetAddress.getLocalHost().hostName
        val serverInstanceId = "$serverId.$serverPort"
        redisTemplate.opsForValue().set("member:${memberInfo.id}:server", serverInstanceId)
    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        val accessor = StompHeaderAccessor.wrap(event.message)
        val sessionId = accessor.sessionId!!
        val memberId = sessionManager.getUser(sessionId)

        sessionManager.remove(sessionId, memberId!!)

        redisTemplate.delete("member:$memberId")
    }
}