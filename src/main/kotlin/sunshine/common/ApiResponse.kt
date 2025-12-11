package sunshine.common

import java.time.Instant

/**
 * API 응답을 표준화하는 레코드.
 */
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: ErrorResponse?,
    val timestamp: Instant
) {
    companion object {
        fun <T> ok(data: T): ApiResponse<T> = ApiResponse(true, data, null, Instant.now())
        fun <T> error(error: ErrorResponse): ApiResponse<T> = ApiResponse(false, null, error, Instant.now())
    }
}
