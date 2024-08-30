package com.xircle.apiserver.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class BaseAccessDeniedHandler(private val objectMapper: ObjectMapper) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        val objectBody = BaseResponse<Unit>(BaseResponseStatus.AUTHORIZATION_ERROR)
        val jsonBody = objectMapper.writeValueAsString(objectBody)

        response?.contentType = "application/json; charset=UTF-8"
        response?.status = HttpServletResponse.SC_FORBIDDEN
        response?.writer.use { writer ->
            writer?.write(jsonBody)
        }
    }
}