package sunshine.common

import org.springframework.http.HttpStatus

/**
 * 애플리케이션 전반에서 사용되는 에러 코드를 정의하는 열거형.
 */
enum class ErrorCode(
    val status: HttpStatus,
    val defaultMessage: String
) {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNSUPPORTED_CITY(HttpStatus.BAD_REQUEST, "지원하지 않는 도시입니다."),
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "외부 서비스 연동 중 오류가 발생했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.")
}
