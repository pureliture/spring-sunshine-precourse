package sunshine.common

/**
 * 에러 응답을 표준화하는 레코드.
 */
data class ErrorResponse(
    val code: String,
    val message: String,
    val details: Map<String, Any>?
) {
    companion object {
        fun of(code: ErrorCode, message: String?, details: Map<String, Any>?): ErrorResponse =
            ErrorResponse(
                code.name,
                message ?: code.defaultMessage,
                details
            )
    }
}
