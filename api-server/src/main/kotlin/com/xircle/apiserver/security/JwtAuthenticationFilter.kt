package com.xircle.apiserver.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.xircle.apiserver.web.TokenProvider
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import com.xircle.core.store.member.MemberStore
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

data class LoginRequest(val email: String, val password: String)

class JwtAuthenticationAttemptFilter(
    authenticationManager: AuthenticationManager,
    private val memberStore: MemberStore,
    private val tokenProvider: TokenProvider,
    private val objectMapper: ObjectMapper
) : UsernamePasswordAuthenticationFilter(authenticationManager) {
    override fun successfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication
    ) {
        val principal: MemberDetails = authResult.principal as MemberDetails
        val member = memberStore.findMemberByEmail(principal.username)

        val token = tokenProvider.createAccessToken(member.id!!)
        val objectBody = BaseResponse<Unit>(BaseResponseStatus.SUCCESS)
        val jsonBody = objectMapper.writeValueAsString(objectBody)

        response.contentType = "application/json; charset=UTF-8"
        response.addHeader("Authorization", token)
        response.status = HttpServletResponse.SC_OK
        response.writer.use { writer ->
            writer.write(jsonBody)
        }
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val objectMapper = jacksonObjectMapper()
        val loginRequest: LoginRequest = objectMapper.readValue(request.inputStream, LoginRequest::class.java)
        val authRequest = UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        return authenticationManager.authenticate(authRequest)
    }
}