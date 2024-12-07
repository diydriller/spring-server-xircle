package com.xircle.userservice.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.common.response.BaseResponseStatus
import com.xircle.common.response.ResponseUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class SecurityExceptionHandler(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint, AccessDeniedHandler {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        ResponseUtil.createExceptionResponse(
            HttpStatus.UNAUTHORIZED,
            BaseResponseStatus.NOT_AUTHENTICATION_ERROR,
            response,
            objectMapper
        )
    }

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        ResponseUtil.createExceptionResponse(
            HttpStatus.FORBIDDEN,
            BaseResponseStatus.NOT_AUTHORIZATION_ERROR,
            response,
            objectMapper
        )
    }
}