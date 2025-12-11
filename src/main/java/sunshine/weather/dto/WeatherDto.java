package sunshine.weather.dto;

import sunshine.infrastructure.openmeteo.OpenMeteoResponse;

public record WeatherDto(
    double temperature,
    double apparentTemperature,
    int weatherCode,
    int humidity,
    double windSpeed,
    String summary
) {
    public WeatherDto(OpenMeteoResponse.Current current, String summary) {
        this(
            current.temperature2m(),
            current.apparentTemperature(),
            current.weatherCode(),
            current.relativeHumidity2m(),
            current.windSpeed10m(),
            summary
        );
    }
}
