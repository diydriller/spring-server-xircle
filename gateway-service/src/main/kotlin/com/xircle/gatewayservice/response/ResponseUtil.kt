package com.xircle.gatewayservice.response

import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class ResponseUtil {
    companion object {
        fun createExceptionResponse(exchange: ServerWebExchange): Mono<Void> {
            exchange.response.setStatusCode(HttpStatus.UNAUTHORIZED)
            exchange.response.headers.contentType = MediaType.APPLICATION_JSON

            val baseResponse = BaseResponse<Any>(BaseResponseStatus.NOT_AUTHENTICATION_ERROR)
            val bytes = baseResponse.toString().toByteArray()

            return exchange.response.writeWith(Mono.just(exchange.response.bufferFactory().wrap(bytes)))
        }
    }
}