package sunshine.weather.service;

import lombok.RequiredArgsConstructor;
import sunshine.city.component.CityCoordinateMapper;
import sunshine.city.component.Coordinate;
import sunshine.weather.component.WeatherSummarizer;
import sunshine.infrastructure.openmeteo.OpenMeteoClient;
import sunshine.infrastructure.openmeteo.OpenMeteoResponse;
import org.springframework.stereotype.Service;
import sunshine.weather.dto.WeatherDto;


@RequiredArgsConstructor
@Service
public class WeatherService {

    private final CityCoordinateMapper cityCoordinateMapper;
    private final OpenMeteoClient openMeteoClient;
    private final WeatherSummarizer weatherSummarizer;

    public WeatherDto getWeather(String city) {
        Coordinate coordinate = cityCoordinateMapper.getCoordinate(city);

        OpenMeteoResponse response = openMeteoClient.fetchWeather(
            coordinate.latitude(),
            coordinate.longitude()
        );

        String summary = weatherSummarizer.summarize(
            city,
            response.current().temperature2m(),
            response.current().windSpeed10m(),
            response.current().weatherCode()
        );

        return new WeatherDto(response.current(), summary);
    }
}
