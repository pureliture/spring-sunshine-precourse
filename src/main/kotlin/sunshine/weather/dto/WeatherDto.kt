package sunshine.weather.dto

import sunshine.infrastructure.openmeteo.OpenMeteoResponse

/**
 * 날씨 정보를 반환하기 위한 DTO.
 */
data class WeatherDto(
    val current: OpenMeteoResponse.Current,
    val summary: String
)
