package sunshine.weather.service;

import org.springframework.stereotype.Service;
import sunshine.city.component.CityCoordinateMapper;
import sunshine.city.component.Coordinate;
import sunshine.infrastructure.openmeteo.OpenMeteoClient;
import sunshine.infrastructure.openmeteo.OpenMeteoResponse;
import sunshine.weather.component.WeatherSummarizer;
import sunshine.weather.dto.WeatherDto;

/**
 * 날씨 정보를 조회하고 요약하는 서비스.
 */
@Service
public class WeatherService {

  private final CityCoordinateMapper cityCoordinateMapper;
  private final OpenMeteoClient openMeteoClient;
  private final WeatherSummarizer weatherSummarizer;

  public WeatherService(
      CityCoordinateMapper cityCoordinateMapper,
      OpenMeteoClient openMeteoClient,
      WeatherSummarizer weatherSummarizer
  ) {
    this.cityCoordinateMapper = cityCoordinateMapper;
    this.openMeteoClient = openMeteoClient;
    this.weatherSummarizer = weatherSummarizer;
  }

  /**
   * 도시 이름을 입력받아 날씨 정보를 반환한다.
   */
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
