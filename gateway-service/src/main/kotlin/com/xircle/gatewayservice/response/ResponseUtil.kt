package com.xircle.gatewayservice.response

import com.fasterxml.jackson.databind.ObjectMapper
import com.xircle.common.response.BaseResponse
import com.xircle.common.response.BaseResponseStatus
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class ResponseUtil {
    companion object {
        private val objectMapper = ObjectMapper()

        fun createAuthenticationErrorResponse(exchange: ServerWebExchange): Mono<Void> {
            exchange.response.setStatusCode(HttpStatus.UNAUTHORIZED)
            exchange.response.headers.contentType = MediaType.APPLICATION_JSON

            val baseResponse = BaseResponse<Unit>(BaseResponseStatus.NOT_AUTHENTICATION_ERROR)
            val jsonBytes = objectMapper.writeValueAsBytes(baseResponse)
            return exchange.response.writeWith(Mono.just(exchange.response.bufferFactory().wrap(jsonBytes)))
        }
    }
}