package com.xircle.apiserver.security

import com.xircle.apiserver.web.TokenProvider
import com.xircle.core.store.member.MemberStore
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


class JwtAuthenticationValidateFilter(
    authenticationManager: AuthenticationManager,
    private val memberStore: MemberStore,
    private val tokenProvider: TokenProvider
) : BasicAuthenticationFilter(authenticationManager) {

    private val excludedPaths = listOf("/member")

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val path = req.requestURI
        if (excludedPaths.contains(path)) {
            chain.doFilter(req, res)
            return
        }

        val token: String? = req.getHeader("Authorization")
        if (token.isNullOrEmpty()) {
            throw InsufficientAuthenticationException("토큰값이 없습니다.")
        }

        try {
            tokenProvider.verifyToken(token)
            val id = tokenProvider.decodeToken(token).getClaim("id").asLong()
            val member = memberStore.findMemberById(id)

            val memberDetails = MemberDetails(member)
            val authentication: Authentication =
                UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: Exception) {
            throw BadCredentialsException("토큰 검증에 실패했습니다.")
        }

        chain.doFilter(req, res)
    }
}