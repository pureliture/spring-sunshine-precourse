package com.sunshine.api.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    CITY_NOT_SUPPORTED(HttpStatus.NOT_FOUND, "지원하지 않는 도시입니다."),
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "외부 날씨 API 호출에 실패했습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다.")
}
