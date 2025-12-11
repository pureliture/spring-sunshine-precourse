package com.sunshine.api.response

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: ErrorResponse?
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(true, data, null)
        }

        fun failure(error: ErrorResponse): ApiResponse<Nothing> {
            return ApiResponse(false, null, error)
        }
    }
}
