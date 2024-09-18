package com.xircle.apiserver.web

import com.xircle.common.exception.ConflictException
import com.xircle.common.exception.ServerErrorException
import com.xircle.common.response.BaseResponse
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.util.function.Consumer

@ControllerAdvice
class BaseExceptionHandler {
    @ExceptionHandler(BadRequestException::class)
    @ResponseBody
    fun handleValidationException(ex: BindException): ResponseEntity<BaseResponse<Any>> {
        val result = ex.bindingResult
        val errorList: MutableList<String> = ArrayList()
        result.fieldErrors.forEach(Consumer { fieldError: FieldError ->
            errorList.add(fieldError.field + " : " + fieldError.defaultMessage + " : rejected value is " + fieldError.rejectedValue)
        })
        return ResponseEntity.badRequest()
            .body(BaseResponse(isSuccess = false, message = errorList.joinToString { "/" }, code = 4000))
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

    @ExceptionHandler(ServerErrorException::class)
    @ResponseBody
    fun handleServerErrorException(ex: ServerErrorException): ResponseEntity<BaseResponse<Any>> {
        return ResponseEntity.internalServerError().body(
            BaseResponse(
                isSuccess = false,
                message = ex.status.message,
                code = ex.status.code
            )
        )
    }
}