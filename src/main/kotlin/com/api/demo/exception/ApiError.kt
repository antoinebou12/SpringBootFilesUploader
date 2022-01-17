package com.api.demo.exception

import org.springframework.http.HttpStatus
import java.util.*
import kotlin.reflect.KClass

data class ApiError(
    val code: String = GenericErrors.UNKNOWN._code,
    val message: String = GenericErrors.UNKNOWN._message,
    val timestamp: Date = Date())

interface ApiErrorInterface {
    fun getHttpStatus(): HttpStatus

    fun getCode(): String

    fun getMessage(): String

    fun getWrappedExceptions(): List<KClass<*>>? {
        return null
    }
}

enum class GenericErrors(val _httpStatus: HttpStatus, val _code: String, val _message: String) : ApiErrorInterface {

    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "ERR0000", "Unknown error"),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "ERR0001", "Validation of field failed"),
    JWT_ERROR(HttpStatus.BAD_REQUEST, "ERR00003", "Authentication error");


    override fun getHttpStatus(): HttpStatus {
        return _httpStatus
    }

    override fun getCode(): String {
        return _code
    }

    override fun getMessage(): String {
        return _message
    }
}

class ApiException : Exception {
    private val apiError: ApiErrorInterface
    private val exception: Exception?
    constructor(apiErrorInterface: ApiErrorInterface): super(apiErrorInterface.getMessage()) {
        this.apiError = apiErrorInterface
        this.exception = null
    }
    constructor(apiErrorInterface: ApiErrorInterface, exception: Exception): super(apiErrorInterface.getMessage(), exception) {
        this.apiError = apiErrorInterface
        this.exception = exception
    }
}
