package com.tm.transfer.application.handler

import com.tm.transfer.application.exceptions.ApiException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ApplicationHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ApiException::class)
    fun handleApiException(apiException: ApiException): ResponseEntity<ErrorResponse> {
        return createErrorResponse(apiException.status, apiException.message, apiException.details)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ErrorResponse> {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.message)
    }

    private fun createErrorResponse(
        status: HttpStatus,
        message: String?,
        details: Map<String, String>? = null
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = status.name,
            message = message ?: "",
            details = details
        )

        return ResponseEntity(errorResponse, status)
    }
}

data class ErrorResponse(
    val errorCode: String,
    val message: String,
    val details: Map<String, String>? = null
)
