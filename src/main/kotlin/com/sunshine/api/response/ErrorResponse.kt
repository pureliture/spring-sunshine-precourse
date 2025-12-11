package com.sunshine.api.response

import com.sunshine.api.error.ErrorCode

data class ErrorResponse(
    val code: String,
    val message: String
) {
    companion object {
        fun from(errorCode: ErrorCode, detail: String?): ErrorResponse {
            val message = detail ?: errorCode.message
            return ErrorResponse(errorCode.name, message)
        }
    }
}
