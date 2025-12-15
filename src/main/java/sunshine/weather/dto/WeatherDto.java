package sunshine.weather.dto;

import sunshine.infrastructure.openmeteo.OpenMeteoResponse;

/**
 * 날씨 정보를 반환하기 위한 DTO.
 */
public record WeatherDto(
    OpenMeteoResponse.Current current,
    String summary
) {
}
