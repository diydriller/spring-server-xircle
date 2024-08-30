package com.xircle.apiserver.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class BaseAuthenticationEntryPoint(private val objectMapper: ObjectMapper): AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val objectBody = BaseResponse<Unit>(BaseResponseStatus.AUTHENTICATION_ERROR)
        val jsonBody = objectMapper.writeValueAsString(objectBody)

        response?.contentType = "application/json; charset=UTF-8"
        response?.status = HttpServletResponse.SC_UNAUTHORIZED
        response?.writer.use { writer ->
            writer?.write(jsonBody)
        }
    }
}