package com.xircle.userservice.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.common.response.BaseResponseStatus
import com.xircle.common.response.ResponseUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class AuthenticationFilter(
    private val memberDetailsService: MemberDetailsService,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    private val publicPaths = arrayOf("/member", "/login")

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.servletPath
        publicPaths.forEach {
            if (path.startsWith(it)) {
                return filterChain.doFilter(request, response)
            }
        }

        try {
            val memberId = request.getHeader("memberId")
            memberDetailsService.loadUserByUsername(memberId)

            request.session?.getAttribute("member")?.let { member ->
                if (member is MemberDetails) {
                    SecurityContextHolder.getContext().apply {
                        authentication = UsernamePasswordAuthenticationToken(member, null, member.authorities)
                    }
                }
            }

            filterChain.doFilter(request, response)
        } catch (ex: Exception) {
            ResponseUtil.createExceptionResponse(
                HttpStatus.UNAUTHORIZED,
                BaseResponseStatus.NOT_AUTHENTICATION_ERROR,
                response,
                objectMapper
            )
        }
    }
}