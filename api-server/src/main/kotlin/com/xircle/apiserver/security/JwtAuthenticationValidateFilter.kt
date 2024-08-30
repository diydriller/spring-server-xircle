package com.xircle.apiserver.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.apiserver.web.TokenProvider
import com.xircle.common.exception.AuthenticationException
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.store.member.MemberStore
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


class JwtAuthenticationValidateFilter(
    authenticationManager: AuthenticationManager,
    private val memberStore: MemberStore,
    private val tokenProvider: TokenProvider,
    private val objectMapper: ObjectMapper
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
            val objectBody = BaseResponse<Unit>(BaseResponseStatus.AUTHENTICATION_ERROR)
            val jsonBody = objectMapper.writeValueAsString(objectBody)

            res.contentType = "application/json; charset=UTF-8"
            res.status = HttpServletResponse.SC_UNAUTHORIZED
            res.writer.use { writer ->
                writer.write(jsonBody)
            }
            return
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
            throw AuthenticationException(BaseResponseStatus.AUTHENTICATION_ERROR)
        }

        chain.doFilter(req, res)
    }
}