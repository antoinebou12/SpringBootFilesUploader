package com.api.project.exception;

import org.springframework.http.HttpStatus


enum class BasicException(
    private val _code: String,
    private val _httpStatus: HttpStatus,
    private val _message: String
) : ApiErrorInterface {

    INVALID_NUMBER("INVALID_NUMBER", HttpStatus.BAD_REQUEST, "The number provided is invalid."),
    NOT_FOUND("NOT_FOUND", HttpStatus.BAD_REQUEST, "The info requested doesn't exist");

    override fun getCode(): String {
        return _code
    }

    override fun getHttpStatus(): HttpStatus {
        return _httpStatus
    }

    override fun getMessage(): String {
        return _message
    }
}

