package com.tm.transfer.application.exceptions

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

open class ApiException(
    message: String,
    val status: HttpStatus,
    throwable: Throwable? = null,
    val details: Map<String, String>? = null,
) : RuntimeException(message, throwable)

class ResourceNotFoundException(message: String, throwable: Throwable? = null):
    ApiException(message, HttpStatus.NOT_FOUND, throwable)

class DatabaseException(message: String, throwable: Throwable):
    ApiException(message, HttpStatus.INTERNAL_SERVER_ERROR, throwable)

class InvalidParameterException(message: String, throwable: Throwable? = null):
    ApiException(message, HttpStatus.BAD_REQUEST, throwable)

class InsufficientBalanceException(message: String, throwable: Throwable? = null):
    ApiException(message, HttpStatus.BAD_REQUEST, throwable)

class InvalidDateScheduledException(message: String, throwable: Throwable? = null):
    ApiException(message, HttpStatus.BAD_REQUEST, throwable)

class InternalException(message: String, throwable: Throwable? = null):
    ApiException(message, HttpStatus.INTERNAL_SERVER_ERROR, throwable)
