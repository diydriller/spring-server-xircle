package com.xircle.gatewayserver.security

import com.xircle.core.store.member.MemberStore
import com.xircle.core.util.TokenProvider
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationValidateFilter(
    private val memberStore: MemberStore,
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
            throw InsufficientAuthenticationException("토큰값이 없습니다.")
        }

        try {
            tokenProvider.verifyToken(token)
        } catch (e: Exception) {
            throw BadCredentialsException("토큰 검증에 실패했습니다.")
        }

        val memberId = tokenProvider.decodeToken(token).getClaim("id").asLong()
        val member = memberStore.findMemberById(memberId)

        val memberDetails = MemberDetails(member)
        val authentication = UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.authorities)
        val context: SecurityContext = SecurityContextImpl(authentication)

        val modifiedRequest = exchange.request.mutate()
            .header("memberId", memberId.toString())
            .build()

        val modifiedExchange = exchange.mutate()
            .request(modifiedRequest)
            .build()

        return chain.filter(modifiedExchange).contextWrite(
            ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context))
        );
    }
}