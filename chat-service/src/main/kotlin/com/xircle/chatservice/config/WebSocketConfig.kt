package com.xircle.chatservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@EnableWebSocketMessageBroker
@Configuration
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    @Value("\${redis.host}")
    private val redisHost: String = ""

    @Value("\${redis.port}")
    private val redisPort: Int = 0

    @Value("\${redis.user}")
    private val redisUser: String = ""

    @Value("\${redis.password}")
    private val redisPassword: String = ""

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableStompBrokerRelay("/topic", "/queue")
            .setRelayHost(redisHost)
            .setRelayPort(redisPort)
            .setClientLogin(redisUser)
            .setClientPasscode(redisPassword)
        config.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*").withSockJS()
    }
}