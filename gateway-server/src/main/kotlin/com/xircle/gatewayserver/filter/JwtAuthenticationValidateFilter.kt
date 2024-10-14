package com.xircle.gatewayserver.filter

import com.xircle.common.token.TokenProvider
import com.xircle.gatewayserver.response.ResponseUtil
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationValidateFilter(
    private val tokenProvider: TokenProvider
) : WebFilter {
    private val publicPaths = arrayOf("/api/member", "/api/login")

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request: ServerHttpRequest = exchange.request
        val path = request.uri.path
        publicPaths.forEach {
            if (path.startsWith(it)) {
                return chain.filter(exchange)
            }
        }

        val token: String? = request.headers.getFirst("Authorization")
        if (token.isNullOrEmpty()) {
            return ResponseUtil.createExceptionResponse(exchange)
        }

        try {
            tokenProvider.verifyToken(token)
        } catch (e: Exception) {
            return ResponseUtil.createExceptionResponse(exchange)
        }

        val memberId = tokenProvider.decodeToken(token).getClaim("id").asString()

        val modifiedRequest = exchange.request.mutate()
            .header("memberId", memberId)
            .build()
        val modifiedExchange = exchange.mutate()
            .request(modifiedRequest)
            .build()
        return chain.filter(modifiedExchange);
    }
}