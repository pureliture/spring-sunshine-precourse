package sunshine.weather.dto;

import sunshine.weather.domain.Weather;

public record WeatherDto(
    double temperature,
    double apparentTemperature,
    int humidity,
    double windSpeed,
    int weatherCode,
    double precipitation,
    double maxTemperature,
    String summary,
    String outfitRecommendation
) {
    public WeatherDto(Weather weather, String summary, String outfitRecommendation) {
        this(weather.temperature(), weather.apparentTemperature(), weather.humidity(),
             weather.windSpeed(), weather.weatherCode(), weather.precipitation(),
             weather.maxTemperature(), summary, outfitRecommendation);
    }
}
