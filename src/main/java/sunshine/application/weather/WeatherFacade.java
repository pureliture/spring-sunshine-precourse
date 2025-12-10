package sunshine.application.weather;

import sunshine.component.city.CityCoordinateMapper;
import sunshine.component.city.Coordinate;
import sunshine.component.weather.WeatherSummarizer;
import sunshine.infrastructure.openmeteo.OpenMeteoClient;
import sunshine.infrastructure.openmeteo.OpenMeteoResponse;
import org.springframework.stereotype.Service;

@Service
public class WeatherFacade {

    private final CityCoordinateMapper cityCoordinateMapper;
    private final OpenMeteoClient openMeteoClient;
    private final WeatherSummarizer weatherSummarizer;

    public WeatherFacade(
        CityCoordinateMapper cityCoordinateMapper,
        OpenMeteoClient openMeteoClient,
        WeatherSummarizer weatherSummarizer
    ) {
        this.cityCoordinateMapper = cityCoordinateMapper;
        this.openMeteoClient = openMeteoClient;
        this.weatherSummarizer = weatherSummarizer;
    }

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
