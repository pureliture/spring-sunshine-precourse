package sunshine.common

/**
 * 비즈니스 로직에서 발생하는 예외를 처리하는 클래스.
 */
class BusinessException(
    val errorCode: ErrorCode,
    cause: Throwable? = null
) : RuntimeException(errorCode.defaultMessage, cause)
