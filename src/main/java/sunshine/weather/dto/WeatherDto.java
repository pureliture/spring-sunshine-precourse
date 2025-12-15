package sunshine.weather.dto;

import sunshine.weather.domain.Weather;

/**
 * 날씨 정보를 반환하기 위한 DTO.
 */
public record WeatherDto(
    Weather weather,
    String summary
) {
}
