package com.xircle.apiserver.web

import com.xircle.common.exception.ConflictException
import com.xircle.common.exception.NotFoundException
import com.xircle.common.exception.ServerErrorException
import com.xircle.common.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class BaseExceptionHandler {
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingParams(ex: MissingServletRequestParameterException): ResponseEntity<BaseResponse<Any>> {
        val errorMessage = "missing parameter: ${ex.parameterName}"
        return ResponseEntity.badRequest().body(BaseResponse(isSuccess = false, message = errorMessage, code = 4000))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse<Any>> {
        val errorMessage = "bad request"
        return ResponseEntity.badRequest().body(BaseResponse(isSuccess = false, message = errorMessage, code = 4000))
    }

    @ExceptionHandler(ConflictException::class)
    @ResponseBody
    fun handleConflictException(ex: ConflictException): ResponseEntity<BaseResponse<Any>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            BaseResponse(
                isSuccess = false,
                message = ex.status.message,
                code = ex.status.code
            )
        )
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseBody
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<BaseResponse<Any>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            BaseResponse(
                isSuccess = false,
                message = ex.status.message,
                code = ex.status.code
            )
        )
    }

    @ExceptionHandler(ServerErrorException::class)
    @ResponseBody
    fun handleServerErrorException(ex: ServerErrorException): ResponseEntity<BaseResponse<Any>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            BaseResponse(
                isSuccess = false,
                message = ex.status.message,
                code = ex.status.code
            )
        )
    }
}