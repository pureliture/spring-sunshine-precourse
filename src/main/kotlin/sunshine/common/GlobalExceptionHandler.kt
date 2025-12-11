package sunshine.common

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 전역 예외 처리기.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: BusinessException): ResponseEntity<ApiResponse<Void>> {
        val errorResponse = ErrorResponse.of(ex.errorCode, ex.message, emptyMap())
        return ResponseEntity.status(ex.errorCode.status)
            .body(ApiResponse.error(errorResponse))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ApiResponse<Void>> {
        val errorResponse = ErrorResponse.of(ErrorCode.VALIDATION_ERROR, ex.message, emptyMap())
        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.status)
            .body(ApiResponse.error(errorResponse))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ApiResponse<Void>> {
        val errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, ex.message, emptyMap())
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.status)
            .body(ApiResponse.error(errorResponse))
    }
}
