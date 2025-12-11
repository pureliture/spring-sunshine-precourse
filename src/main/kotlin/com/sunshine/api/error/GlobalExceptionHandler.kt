package com.sunshine.api.error

import com.sunshine.api.response.ApiResponse
import com.sunshine.api.response.ErrorResponse
import com.sunshine.application.exception.WeatherException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(exception: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        val message = exception.bindingResult.fieldErrors.firstOrNull()?.defaultMessage
        val body = ApiResponse.failure(ErrorResponse.from(ErrorCode.INVALID_INPUT, message))
        return ResponseEntity.status(ErrorCode.INVALID_INPUT.status).body(body)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraint(exception: ConstraintViolationException): ResponseEntity<ApiResponse<Nothing>> {
        val message = exception.constraintViolations.firstOrNull()?.message
        val body = ApiResponse.failure(ErrorResponse.from(ErrorCode.INVALID_INPUT, message))
        return ResponseEntity.status(ErrorCode.INVALID_INPUT.status).body(body)
    }

    @ExceptionHandler(WeatherException::class)
    fun handleWeather(exception: WeatherException): ResponseEntity<ApiResponse<Nothing>> {
        val body = ApiResponse.failure(ErrorResponse.from(exception.errorCode, exception.message))
        return ResponseEntity.status(exception.errorCode.status).body(body)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknown(exception: Exception): ResponseEntity<ApiResponse<Nothing>> {
        val body = ApiResponse.failure(ErrorResponse.from(ErrorCode.INTERNAL_ERROR, exception.message))
        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.status).body(body)
    }
}
