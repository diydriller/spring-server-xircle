package com.xircle.apiserver.response

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus

class ResponseUtil {
    companion object {
        fun createExceptionResponse(
            httpStatus: HttpStatus,
            responseStatus: BaseResponseStatus,
            response: HttpServletResponse,
            objectMapper: ObjectMapper
        ){
            val errorResponse = BaseResponse<Any>(
                isSuccess = false,
                message = responseStatus.message,
                code = responseStatus.code
            )

            response.contentType = "application/json; charset=UTF-8"
            response.status = httpStatus.value()
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
        }
    }
}